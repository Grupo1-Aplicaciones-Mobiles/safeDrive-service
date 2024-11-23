package com.securecar.safedrive.vehicles.domain.services;

import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;
import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.queries.GetCoordinatesByVehicleIdQuery;
import com.securecar.safedrive.vehicles.domain.model.queries.GetVehicleByIdQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
    List<Vehicle> getVehiclesByUser(String username);
    Optional<Coordinates> handle(GetCoordinatesByVehicleIdQuery query);
}
