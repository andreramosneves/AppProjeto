//var openStreetMapLayer = new ol.layer.Tile({
// source: new ol.source.OSM()
//});
//meuMapa.addLayer(openStreetMapLayer);

 //meuMapa = new ol.Map({
 //target: 'MeuMapa',
 //renderer: 'canvas',
// view: new ol.View({
 //projection: 'EPSG:900913',
 //center: [-5193252.61684,-2698365.38923],
 //zoom: 18
 //})
 //});
 
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
