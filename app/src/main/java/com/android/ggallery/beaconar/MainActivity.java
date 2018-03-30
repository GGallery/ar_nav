package com.android.ggallery.beaconar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

public class MainActivity extends AppCompatActivity {

    private Handler scanHandler = new Handler();
    private int scan_interval_ms = 2000;
    private Integer actualRoom=-9999;
    ArchitectView architectView;
    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,GgalleryBeaconListenerIntentService.class));
        scanHandler.post(scanRunnable);
        global=(Global)getApplicationContext();
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey("XjM9jYAU53+ub8+M++ir2kCMNBHl18i2XWeUsgED++IvXKDrA1Vnd+2bhTmrlt4ZQ9T1o4umhhv4YujW3BkD7/gz3clZioIwdrrPaQZqL7ukbFMUFmRe4DlEpHc1ubgc3BSs6+EJteSNnTdZqhIUbHRdLRXxmm3TaER7UlJ9XH9TYWx0ZWRfX/GjgL7r3OdSuAdOcyqhm3EcwEUI9dzCXv0Xhz8W3SNWju4oZtwEfSoQu1E0pbaBja7emhUZQ4W7X0ObfoLs9/YcSLDEJw9n8nygFp5g8VVH2jBXakeKtFfnJNcLs2ClhNsetQdOjwY7llZlJgxQIS59jOg11vp7CLb3375Sjazck2cwa5KuwPqTQqRaO4viDycC2ULeqQqA+ziJGyzhwZHLeWBEWEmiud/+bRzkEpOp6WWTQqoCO7wAx+8eyzGUdeTesPuftdJltCA6OGvNYzdCJuHj6F+yg+GBHvLn6JOIxeGnfJ4xe7Nei7jPNgbM4TbOeR6VT2xqEbLwl9eAEWuqUEWbN04Lhuq0i4h3/KbH5GtAQiyzW6SMGg1rxXNkbVNSAcV8Y5Jh4nuvCT8BdJVDNta+9Ircavzog7AKaUr2Su8/I7F5lpt81oKlbAejb4iHBQO14COeVgYYaGIa+DF3kK+AH5FfS5W7C7CsT8kntfAio3E0ncw=");
        this.architectView.onCreate( config );
    }

    private Runnable scanRunnable = new Runnable()
    {
        @Override
        public void run() {

            if(global.getNearestBeacon()!=null) {
                Log.i("ACTUAL UUID NEAREST", global.getNearestBeacon().getUuid());
                Log.i("ACTUAL MINOR NEAREST", global.getNearestBeacon().getMinor().toString());
                Log.i("ACTUAL MAJOR NEAREST", global.getNearestBeacon().getMajor().toString());
                openRoomAR(global.getNearestBeacon().getMajor());


            }else{
                Log.i("ACTUAL UUID NEAREST","nessuno");
                closeRoomAR();
            }
            scanHandler.postDelayed(this, scan_interval_ms);
        }
    };

    private void openRoomAR(Integer major){

        if(major!=actualRoom){

            actualRoom=major;

            try {
                this.architectView.load("LOCALAPP/"+actualRoom.toString()+"/index.html");

            }catch (Exception e){

                Log.e("errore nel caricamento", e.getMessage());
            }

        }

    }

    private  void closeRoomAR(){
        try {
            this.architectView.load("null");

        }catch (Exception e){

            Log.e("errore nel caricamento", e.getMessage());
        }


    }

    protected void OnStart(){

        super.onStart();



    }
    @Override
    public void onResume() {

        super.onResume();


        this.architectView.onResume();

        /*if (this.sensorAccuracyListener!=null) {
            this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
        }*/
    }

    // tell locationProvider to resume, usually location is then (again) fetched, so the GPS indicator appears in status bar
    /*if ( this.locationProvider != null ) {
        this.locationProvider.onResume();
    }*/
    @Override
    protected void onPostCreate( final Bundle savedInstanceState ) {

        super.onPostCreate( savedInstanceState );
        this.architectView.onPostCreate();

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();

        //double lat=44.453032;
        //double lon=9.09496;
        //architectView.setLocation(lat,lon,1);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onPause();

            // unregister accuracy listener in architectView, if set
          /*  if ( this.sensorAccuracyListener != null ) {
                this.architectView.unregisterSensorAccuracyChangeListener( this.sensorAccuracyListener );
            }*/
        }

        // tell locationProvider to pause, usually location is then no longer fetched, so the GPS indicator disappears in status bar
        /*if ( this.locationProvider != null ) {
            this.locationProvider.onPause();
        }*/
    }

    @Override
    protected void onStop() {

        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onDestroy();
        }
    }

}
