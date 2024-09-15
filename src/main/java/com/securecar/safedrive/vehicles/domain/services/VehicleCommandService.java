package com.securecar.safedrive.vehicles.domain.services;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.commands.CreateVehicleCommand;
import com.securecar.safedrive.vehicles.domain.model.commands.UpdateVehicleCommand;

import java.util.Optional;

public interface VehicleCommandService {
    Optional<Vehicle> handle(CreateVehicleCommand createVehicleCommand);
    Optional<Vehicle> handle(UpdateVehicleCommand updateVehicleCommand);
}
