var meuMapa;
var userName = getCookie('usuario');//document.getElementById('userValue').value;

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function init() {
    meuMapa = new ol.Map({
        target: MeuMapa,
        renderer: 'canvas',
        view: new ol.View({
            projection: 'EPSG:900913',
            center: [0, 0],
            zoom: 3
        })
    });
    var openStreetMapLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
    });
    meuMapa.addLayer(openStreetMapLayer);
    var bingLayer = new ol.layer.Tile({
        source: new ol.source.BingMaps({
            imagerySet: 'Aerial',
            key: 'Ak-dzM4wZjSqTlzveKz5u0d4IQ4bRzVI309GxmkgSVr1ewS6iPSrOvOKhACJlm3'
        })
    });
    bingLayer.setOpacity(.3);
    meuMapa.addLayer(bingLayer);
    var pointStyle = new ol.style.Style({
        image: new ol.style.Icon(({
            anchor: [0, 0],
            anchorXUnits: 'fraction',
            anchorYUnits: 'fraction',
            opacity: 0.75,
            src: 'dados/r2d2.png'
        }))
    });



//apresenta no console de execução do javascript o valor da variável userName

    console.log(userName);

//define a url da chamada REST (GET)

    var urlString = 'http://localhost:8081/AppFrontController/LP3Rest/lp3/posicoes/';

//concatena o nome do usuário à consulta REST

    urlString = urlString.concat(userName);

//executa a chamada ao webservice REST, recebe a resposta em um vetor de objetos JSON chamado data

//chama a função plotOnMap passando o vetor como parametro.

    $.ajax({
        url: urlString,
        data: {
            format: 'json'
        },
        success: function (data) {
            console.log(data);
            plotOnMap(data);
        },
        error: function (e) {
            console.log(e.message);
        },
        type: 'GET'
    });


//Function que recebe a lista de objetos e plota os pontos

    function plotOnMap(json) {

        // cria arranjo de features
        var features = new Array();

        console.log(json.length);
        for (var i = 0; i < json.length; i++) {

            // cria ponto a partir de coordenadas em graus
            var point = [parseFloat(json[i].lon), parseFloat(json[i].lat)];

            // converte para coordenadas de tela
            var pos = ol.proj.fromLonLat(point);

            // cria feature

            var feature = new ol.Feature(new ol.geom.Point(pos));

            // define o estilo da feature
            feature.setStyle(pointStyle);

            //adiciona feature a lista de features

            features[features.length] = feature;
        }

        // cria um vetor de features para colocação em uma camada de visualização 
        var source = new ol.source.Vector({
            features: features
        });

        //cria uma camada com o vetor de features
        featuresLayer = new ol.layer.Vector({
            source: source,
            style: pointStyle
        });

        //adiciona a camada ao mapa

        meuMapa.addLayer(featuresLayer);
        var element = document.getElementById('popup');
        var popup = new ol.Overlay({
            element: element,
            positioning: 'bottom-center',
            stopEvent: false
        });
        meuMapa.addOverlay(popup);
        meuMapa.on('click', function (evt) {
            var feature = meuMapa.forEachFeatureAtPixel(evt.pixel,
                    function (feature, layer) {
                        return feature;
                    });
            if (feature) {
                popup.setPosition(evt.coordinate);
//                var xmlString;
                var urlString = 'http://gateway.marvel.com/v1/public/characters?nameStartsWith=Captain%20America&ts=05272016072544&apikey=f33eb15f5b1bf8d7da85f6d1d206f83c&hash=256693f22a9c43c88190e8005d9ebb05';
//                var urlString = 'http://localhost:8081/AppFrontController/LP3Rest/lp3/posicoesXML/';
                console.log(urlString);
//                urlString = urlString.concat(userName);
//                console.log(urlString);
                $.ajax({
                    url: urlString,
                    data: {
//                        format: 'json'
                    },
                    success: function (data) {
//                        xmlString = (new XMLSerializer()).serializeToString(data);
//                            console.log(dataJ['total']);
                            var dataJ = data['data'];
                            var results = dataJ['results'];
                            var thumbnail = results[1].thumbnail;
                        console.log(data['attributionHTML']);
                        $(element).popover({
                            'placement': 'top',
                            'html': true,
//                           content': '<img src=' + data['data'].results['name'] + '/>'
                            'content': '<img height="100" width="100" src=' + thumbnail['path'] + '.jpg' + ' />'+
                                    '<p>' + userName + '</p>'
                        });
                        $(element).popover('show');
                    },
                    error: function (e) {
                        console.log(e.message);
                    },
                    type: 'GET'
                });
            } else {
                $(element).popover('destroy');
            }
        });
        meuMapa.on('pointermove', function (e) {
            if (e.dragging) {
                $(element).popover('destroy');
                return;
            }
            var pixel = meuMapa.getEventPixel(e.originalEvent);
            var hit = meuMapa.hasFeatureAtPixel(pixel);
        });
    }
}
