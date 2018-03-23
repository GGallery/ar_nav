
function start(){

var radarCircle = new AR.Circle(0.03, {
    horizontalAnchor: AR.CONST.HORIZONTAL_ANCHOR.CENTER,
    opacity: 0.8,
    style: {
        fillColor: "#ffffff"
    }
}); 
var radardrawables = [];
radardrawables.push(radarCircle);	

var directionIndicatorDrawable = new AR.ImageDrawable(new AR.ImageResource("assets/indi.png"), 0.2, {
    enabled: true
});

var camera = new AR.ImageDrawable(new AR.ImageResource("assets/camera.png"),2,{

	enabled:true,
});

var ristorante = new AR.ImageDrawable(new AR.ImageResource("assets/ristorante.png"),2,{

	enabled:true,
});

var camera_loc = new AR.RelativeLocation(null,0,5,5);
var ristorante_loc = new AR.RelativeLocation(null,5,0,0);

var camera_geo =new AR.GeoObject(camera_loc,{
    drawables:{
        cam:[camera],
		indicator: directionIndicatorDrawable,
        radar: radardrawables
    },
	enabled:true
});

var ristorante_geo =new AR.GeoObject(ristorante_loc,{
    drawables:{
        cam:[ristorante],
		indicator: directionIndicatorDrawable,
        radar: radardrawables
    },
	enabled:true
});
PoiRadar.show();
return null;
	
}	//QUI FINISCE START



//AR.context.onLocationChanged = start;
//AR.context.scene.minScalingDistance=1;