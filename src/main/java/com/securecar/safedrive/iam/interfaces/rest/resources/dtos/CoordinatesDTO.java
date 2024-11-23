package com.securecar.safedrive.iam.interfaces.rest.resources.dtos;

public class CoordinatesDTO {
    private double latitude;
    private double longitude;

    public CoordinatesDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}