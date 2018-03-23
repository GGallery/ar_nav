package com.android.ggallery.beaconar;

import android.app.Application;

public class Global extends Application {

    Beacon NearestBeacon;

    public Beacon getNearestBeacon(){

        return NearestBeacon;
    }

    public void setNearestBeacon(Beacon nearestbeacon){

        NearestBeacon=nearestbeacon;
    }

}