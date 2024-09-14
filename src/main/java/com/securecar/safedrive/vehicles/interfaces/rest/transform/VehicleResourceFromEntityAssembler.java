package com.securecar.safedrive.vehicles.interfaces.rest.transform;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle vehicle) {
        return new VehicleResource(vehicle.getMarca(), vehicle.getModelo(), vehicle.getColor(), vehicle.getPlaca());
    }
}
