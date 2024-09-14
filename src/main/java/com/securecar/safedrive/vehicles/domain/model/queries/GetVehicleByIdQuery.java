package com.securecar.safedrive.vehicles.domain.model.queries;

public record GetVehicleByIdQuery(  Long id) {
    public GetVehicleByIdQuery {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");
    }
}
