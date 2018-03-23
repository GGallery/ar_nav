package com.android.ggallery.beaconar;

public class Beacon {

    private String Uuid;
    private Integer Rssi;

    public Beacon(String uuid, Integer rssi){

        this.Uuid=uuid;
        this.Rssi=rssi;

    }

    public String getUuid(){

        return this.Uuid;
    }

    public  Integer getRssi(){

        return this.Rssi;
    }



}