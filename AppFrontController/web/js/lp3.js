// carrega na variável userName o valor do campo escondido com id="userValue"

//var userName = document.getElementById('userValue').value;
var userName = "denis";
//apresenta no console de execução do javascript o valor da variável userName
console.log(userName);
var container;
var content;
var closer;
//define a url da chamada REST (GET)

var urlString = 'http://localhost:8081/AppFrontController/LP3Rest/lp3/posicoes/';

//concatena o nome do usuário à consulta REST
urlString = urlString.concat(userName);
//urlString2 = urlString2.concat(userName);

var pointStyle = new ol.style.Style({
    image: new ol.style.Icon(({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'dados/r2d2.png'
    }))
});
//var pointStyle = new ol.style.Style({
//    image: new ol.style.Icon(({
//        anchor: [0, 0],
//        anchorXUnits: 'fraction',
//        anchorYUnits: 'fraction',
//        opacity: 0.75,
//        src: 'dados/r2d2.png'
//    }))
//});

var meuMapa;
function init() {
    container = document.getElementById('popup');
    content = document.getElementById('popup-content');
    closer = document.getElementById('popup-closer');
    var overlay = new ol.Overlay(({
        element: container,
        autoPan: true,
        autoPanAnimation: {
            duration: 250
        }
    }));


    meuMapa = new ol.Map({
        target: 'MeuMapa',
        renderer: 'canvas',
        overlays: [overlay],
        view: new ol.View({
            projection: 'EPSG:900913',
            center: [-5193252.61684, -2698365.38923],
            zoom: 18
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
    closer.onclick = function () {
        overlay.setPosition(undefined);
        closer.blur();
        return false;
    };

    meuMapa.on('singleclick', function (evt) {
        var coordinate = evt.coordinate;
        var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate, 'EPSG:900913',
                'EPSG:4326'));
        content.innerHTML = '<p>Posição Atual:</p><code>' + hdms + '</code>';
        overlay.setPosition(coordinate);
    });

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

}
//Define um estilo para os pontos a serem plotados.

//Indica o icone que desejamos utilizar para representar as posições



//Function que recebe a lista de objetos e plota os pontos

function plotOnMap(json) {

    // cria arranjo de features
    var features = new Array();

    console.log(json.length);
    var count = 0;
    for (var i = 0; i < json.length; i++) {

        // cria ponto a partir de coordenadas em graus
        var point = [json[i].lon, json[i].lat];

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
        source: new ol.source.Vector({
            projection: 'EPSG:4326',
            format: new ol.format.GPX(),
            url: 'dados/posicao_proc.gpx'
        }),
        style: pointStyle
    });

    //adiciona a camada ao mapa
    meuMapa.addLayer(featuresLayer);


}
