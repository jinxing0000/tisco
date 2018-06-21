//底图对象创建
var map;
//是否测距中
var Drawing = false;
var DRAWLAYERS = [];
var DrawPolyLine; //绘制的折线
var DrawingPolyLine; //绘制过程中的折线
var DrawingPolyPoints = []; //绘制的折线的节点集

var ISMEASURE = false;  //是否是量距
var MEASURETOOLTIP;  //量距提示
var MEASUREAREATOOLTIP;  //量面提示
var MEASURERESULT = 0;

var DrawPoints = [];//图层点集

//底图准备
var dlgLayer;

//图层点集
var rainALLMarkers = [];
var waterZXMarkers = [];
var stZXMarkers = [];

//交通图
var _yxLayer1 = new L.tileLayer('http://t{s}.tianditu.cn/DataServer?T=vec_c&X={x}&Y={y}&L={z}', { subdomains: [0, 1, 2, 3, 4, 5, 6, 7], minZoom: 0, maxZoom: 24, maxNativeZoom: 20, multi: [{ minZoom: 14, maxZoom: 17, url: 'http://www.zjditu.cn:88/ZJEMAP/wmts.asmx/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=ZJEMAP&FORMAT=image/png&TILEMATRIXSET=TileMatrixSet0&TILEMATRIX={z}&STYLE=default&TILEROW={y}&TILECOL={x}' }, { minZoom: 17, maxZoom: 20, url: 'http://www.zsch.gov.cn/ogcservice/SSEMAP/wmts.asmx/WMTS?Service=WMTS&VERSION=1.0.0&Request=GetTile&Layer=SSEMAP&Format=image/png&TileMatrixSet=TileMatrixSet0&TileMatrix={z}&Style=default&TileRow={y}&TileCol={x}' }] });
var _textYXLayer1 = new L.tileLayer('http://t{s}.tianditu.cn/DataServer?T=cva_c&X={x}&Y={y}&L={z}', { subdomains: [0, 1, 2, 3, 4, 5, 6, 7], minZoom: 0, maxZoom: 21, maxNativeZoom: 20, multi: [{ minZoom: 14, maxZoom: 17, url: 'http://www.zjditu.cn:88/ZJEMAPANNO/wmts.asmx/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=ZJEMAPANNO&FORMAT=image/png&TILEMATRIXSET=TileMatrixSet0&TILEMATRIX={z}&STYLE=default&TILEROW={y}&TILECOL={x}' }, { minZoom: 17, maxZoom: 20, url: 'http://www.zsch.gov.cn/ogcservice/SSEMAPANNO/wmts.asmx/WMTS?Service=WMTS&VERSION=1.0.0&Request=GetTile&Layer=SSEMAPANNO&Format=image/png&TileMatrixSet=TileMatrixSet0&TileMatrix={z}&Style=default&TileRow={y}&TileCol={x}' }] });
normalLayer = L.layerGroup([_yxLayer1, _textYXLayer1]);
//map.addLayer(normalLayer);

//影像图
var _yxLayer2 = new L.tileLayer('http://t{s}.tianditu.cn/DataServer?T=img_c&X={x}&Y={y}&L={z}', { subdomains: [0, 1, 2, 3, 4, 5, 6, 7], minZoom: 0, maxZoom: 21, maxNativeZoom: 20, multi: [{ minZoom: 14, maxZoom: 17, url: 'http://srv.zjditu.cn/ZJDOM_2D/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=ZJDOM2W1&FORMAT=image/jpeg&TILEMATRIXSET=Matrix_0&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}' }, { minZoom: 17, maxZoom: 21, url: 'http://www.zsch.gov.cn/ogcservice/SSIMG/service/wmts?Service=WMTS&VERSION=1.0.0&Request=GetTile&Layer=SSIMG&Format=image/png&TileMatrixSet=TileMatrixSet0&TileMatrix={z}&Style=default&TileRow={y}&TileCol={x}' }] });
var _textYXLayer2 = new L.tileLayer('http://t{s}.tianditu.cn/DataServer?T=cia_c&X={x}&Y={y}&L={z}', { subdomains: [0, 1, 2, 3, 4, 5, 6, 7], minZoom: 0, maxZoom: 21, maxNativeZoom: 20, multi: [{ minZoom: 14, maxZoom: 17, url: 'http://www.zjditu.cn:88/ZJIMGANNO/wmts.asmx/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&LAYER=ZJIMGANNO&FORMAT=image/png&TILEMATRIXSET=TileMatrixSet0&TILEMATRIX={z}&STYLE=default&TILEROW={y}&TILECOL={x}' }, { minZoom: 17, maxZoom: 21, url: 'http://www.zsch.gov.cn/ogcservice/SSIMGANNO/service/wmts?Service=WMTS&VERSION=1.0.0&Request=GetTile&Layer=SSIMGANNO&&Format=image/png&TileMatrixSet=TileMatrixSet0&TileMatrix={z}&Style=default&TileRow={y}&TileCol={x}' }] });

//icon点样式
var stationIcon = L.icon({
    iconUrl: '/wuzi/static/leaflet/images/marker-icon.png',
    iconRetinaUrl: '',
    iconSize: [25, 41],
    iconAnchor: [],
    popupAnchor: [],
    shadowUrl: '/wuzi/static/leaflet/images/marker-shadow.png',
    shadowRetinaUrl: '',
    shadowSize: [],
    shadowAnchor: []
});
//divIcon点样式
var divIcon1 = L.divIcon({ className: 'normal-stationicon' });
var divIcon2 = L.divIcon({ className: 'warnning-stationicon' });
var divIcon3 = L.divIcon({ className: 'check-stationicon' });
var divIcon4 = L.divIcon({ className: '.check-warnicon' });
var divIcon5 = L.divIcon({ className: 'stationicon' });

//边界样式
var borderLine = {
    color: "red",
    weight: 2,
    opacity: 0.5,
    dashArray: "3"
};

//边界图层
var _borderLine;

//水雨情图层

//工程图层
var _cityLayer//雨量站
var _storeLayer;//水位站
var _stationLayer;//测站

//等值线、面图层
var _lineLayer;
var _pageLayer;
function initMap(){
    map = L.map('map').setView([30.297729,120.160831], 13);
    dlgLayer= L.layerGroup([_yxLayer2, _textYXLayer2]);
    map.addLayer(dlgLayer);
    _cityLayer = new L.featureGroup([]);
    map.addLayer(_cityLayer);
    _storeLayer = new L.featureGroup([]);
    map.addLayer(_storeLayer);
}




function setCityPoint(data) {
    //清除图层
    var rows = data.rows;
    rainRows = rows;
    for (var i = 0; i < data.total; i++) {
        var J = data.rows[i].J;
        var W = data.rows[i].W;
        var T =data.rows[i].T;
        var A=data.rows[i].address;
        var P=data.rows[i].phone;
        var top=data.rows[i].top;
        var rainfall = parseFloat(data.rows[i].total);
        var image = '/wuzi/static/leaflet/images/cityicon.png';
        //创建图标对象
        var cityIcon = L.icon({
            iconUrl: image,
            iconSize: [26, 31],
            iconAnchor: [13, 15],
            popupAnchor: [0, -15]
        });

        //设置数据点
        //var boolLv = map.getZoom() >= 15 ? true : false;
        var pointFeature = new L.marker([W, J], { icon: cityIcon, riseOnHover: true, title:"666"}).bindLabel("<span>"+T+"</span>",{noHide:true});


        //绑定弹出框
        var htmlText = '<div style="width:237px;height:118px;"><h1>'+T+'</h1><p>'+P+'</p><p>'+A+'</p><p>'+top+'</p></div>';
        var popup = L.popup({ maxWidth: 900, maxHeight: 800 })
            .setLatLng([W, J])
            .setContent(htmlText);
        pointFeature.bindPopup(popup);

        //点添加进点集数组
        //将数据点添加到相应图层
        _cityLayer.addLayer(pointFeature);
        _cityLayer.addTo(map);
    }
}
function setStorePoint(data) {
    //清除图层
    var rows = data.rows;
    rainRows = rows;
    for (var i = 0; i < data.total; i++) {
        var J = data.rows[i].J;
        var W = data.rows[i].W;
        var T =data.rows[i].T;
        var A=data.rows[i].address;
        var P=data.rows[i].phone;
        var top=data.rows[i].top;
        var rainfall = parseFloat(data.rows[i].total);
        var image = '/wuzi/static/leaflet/images/storeicon.png';
        //创建图标对象
        var storeIcon = L.icon({
            iconUrl: image,
            iconSize: [16,20],
            iconAnchor: [8, 10],
            popupAnchor: [0, -10]
        });

        //设置数据点
        //var boolLv = map.getZoom() >= 15 ? true : false;
        var pointFeature = new L.marker([W, J], { icon: storeIcon, riseOnHover: true, title:"666"}).bindLabel("<span>"+T+"</span>");


        //绑定弹出框
        var htmlText = '<div style="width:237px;height:118px;"><h1>'+T+'</h1><p>'+P+'</p><p>'+A+'</p><p>'+top+'</p></div>';
        var popup = L.popup({ maxWidth: 900, maxHeight: 800 })
            .setLatLng([W, J])
            .setContent(htmlText);
        pointFeature.bindPopup(popup);

        //点添加进点集数组
        //将数据点添加到相应图层
        _storeLayer.addLayer(pointFeature);
        _storeLayer.addTo(map);
    }
}
function elementShow(Lat, Lon,T,A,P,top, STCD) {

    map.setView([Lon, Lat], 16, { zoom: { animate: true } });
    var htmlText = '<div style="width:237px;height:118px;"><h1>'+T+'</h1><p>'+P+'</p><p>'+A+'</p><p>'+top+'</p></div>';
    var popup = L.popup({ maxWidth: 900, maxHeight: 800 })
        .setLatLng([Lon, Lat])
        .setContent(htmlText);
    map.openPopup(popup);
}
function showLatlng(){
   map.on("mousemove",function(e){
       //console.log(e.latlng.lat+'/'+e.latlng.lng);
       $(".latlngbox p:first-child").text("经度："+ e.latlng.lng);
       $(".latlngbox p:last-child").text("纬度："+ e.latlng.lat);
   })
}






















