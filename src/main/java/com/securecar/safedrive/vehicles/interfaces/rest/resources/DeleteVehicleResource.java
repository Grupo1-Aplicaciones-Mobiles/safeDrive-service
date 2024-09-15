package com.securecar.safedrive.vehicles.interfaces.rest.resources;

public record DeleteVehicleResource(Long id) {
    public DeleteVehicleResource {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
    }
}
