package com.securecar.safedrive.vehicles.interfaces.rest.resources.dtos;

import lombok.Getter;

@Getter
public class CoordinatesDTO {
    private final double latitude;
    private final double longitude;

    public CoordinatesDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
