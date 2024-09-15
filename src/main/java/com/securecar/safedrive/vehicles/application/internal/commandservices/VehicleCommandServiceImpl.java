package com.securecar.safedrive.vehicles.application.internal.commandservices;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.commands.CreateVehicleCommand;
import com.securecar.safedrive.vehicles.domain.model.commands.DeleteVehicleCommand;
import com.securecar.safedrive.vehicles.domain.services.VehicleCommandService;
import com.securecar.safedrive.vehicles.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {

    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(CreateVehicleCommand command) {
        if (vehicleRepository.existsByPlaca(command.placa()))
            throw new IllegalArgumentException("Vehicle with same Placa already exists");
        var vehicle = new Vehicle(command);
        var createdVehicle = vehicleRepository.save(vehicle);
        return Optional.of(createdVehicle);
    }

    @Override
    public void handle(DeleteVehicleCommand command) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(command.id());
        if (vehicle.isPresent()) {
            vehicleRepository.delete(vehicle.get());
        } else {
            throw new IllegalArgumentException("Vehicle with ID " + command.id() + " not found");
        }
    }
}
