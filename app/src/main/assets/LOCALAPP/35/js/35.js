
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
var video = new AR.VideoDrawable("assets/GNV.mp4", 2, {
   translate: {
       x: 2,
       y: 0
   },
   enabled:false,
   onClick:function(){
       sala_bambini.enabled=false;
   	   video.play();
   	}
   });

var sala_bambini = new AR.ImageDrawable(new AR.ImageResource("assets/sala_bambini.png"),2,{

	enabled:true,
	onClick:function(){
	   video.enabled=true;
	   video.play();
	}
});

var shopping = new AR.ImageDrawable(new AR.ImageResource("assets/shopping.png"),2,{

	enabled:true,
});


var camera_loc = new AR.RelativeLocation(null,0,5,5);
var sala_bambini_loc = new AR.RelativeLocation(null,5,-5,0);
var shopping_loc = new AR.RelativeLocation(null,5,5,0);


var camera_geo =new AR.GeoObject(camera_loc,{
    drawables:{
        cam:[camera],
		indicator: directionIndicatorDrawable,
        radar: radardrawables
    },
	enabled:true
});

var sala_bambini_geo =new AR.GeoObject(sala_bambini_loc,{
    drawables:{
        cam:[sala_bambini,video],
		indicator: directionIndicatorDrawable,
        radar: radardrawables
    },
	enabled:true
});

var shopping_geo =new AR.GeoObject(shopping_loc,{
    drawables:{
        cam:[shopping],
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