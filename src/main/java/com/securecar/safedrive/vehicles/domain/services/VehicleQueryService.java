package com.securecar.safedrive.vehicles.domain.services;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.queries.GetVehicleByIdQuery;

import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
}
