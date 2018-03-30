package com.android.ggallery.beaconar;

public class Beacon {

    private String Uuid;
    private Integer Rssi;
    private Integer Major;
    private Integer Minor;

    public Beacon(String uuid, Integer rssi, Integer major, Integer minor){

        this.Uuid=uuid;
        this.Rssi=rssi;
        this.Major=major;
        this.Minor=minor;


    }

    public String getUuid(){

        return this.Uuid;
    }

    public  Integer getRssi(){

        return this.Rssi;
    }

    public Integer getMajor(){

        return this.Major;
    }

    public Integer getMinor(){

        return this.Minor;
    }


}