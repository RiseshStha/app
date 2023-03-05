package com.codewithrisesh.ramro;

public class WatchAds {
    private String date;

    public WatchAds(String date, byte numberOfWatchedAds) {
        this.date = date;
        this.numberOfWatchedAds = numberOfWatchedAds;
    }
    public WatchAds(){

    }

    private byte numberOfWatchedAds;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte getNumberOfWatchedAds() {
        return numberOfWatchedAds;
    }

    public void setNumberOfWatchedAds(byte numberOfWatchedAds) {
        this.numberOfWatchedAds = numberOfWatchedAds;
    }


}
