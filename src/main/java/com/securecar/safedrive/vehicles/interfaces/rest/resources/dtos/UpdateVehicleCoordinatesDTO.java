package com.securecar.safedrive.vehicles.interfaces.rest.resources.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVehicleCoordinatesDTO {

    @NotNull
    private Long vehicleId;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;
}
