package com.example.apothecary.helper;

public class CurrentLocation {
    private static double latitude;
    private static double longitude;

    public CurrentLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }
}
