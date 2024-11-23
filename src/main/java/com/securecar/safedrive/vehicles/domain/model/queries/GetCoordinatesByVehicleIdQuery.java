package com.securecar.safedrive.vehicles.domain.model.queries;

public class GetCoordinatesByVehicleIdQuery {
    private final Long vehicleId;

    public GetCoordinatesByVehicleIdQuery(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
}
