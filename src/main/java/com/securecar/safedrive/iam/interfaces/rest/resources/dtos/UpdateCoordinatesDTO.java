package com.securecar.safedrive.iam.interfaces.rest.resources.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCoordinatesDTO {

    @NotNull
    private Long userId;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

}