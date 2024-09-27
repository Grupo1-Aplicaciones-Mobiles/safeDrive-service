package com.securecar.safedrive.vehicles.application.internal.commandservices;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.commands.CreateVehicleCommand;
import com.securecar.safedrive.vehicles.domain.model.commands.DeleteVehicleCommand;
import com.securecar.safedrive.vehicles.domain.model.commands.UpdateVehicleCommand;
import com.securecar.safedrive.vehicles.domain.services.VehicleCommandService;
import com.securecar.safedrive.vehicles.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {


    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;

    @Autowired
    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    //    @Override
    //    public Optional<Vehicle> handle(CreateVehicleCommand command) {
    //        if (vehicleRepository.existsByPlaca(command.placa()))
    //            throw new IllegalArgumentException("Vehicle with same Placa already exists");
    //        var vehicle = new Vehicle(command);
    //        var createdVehicle = vehicleRepository.save(vehicle);
    //        return Optional.of(createdVehicle);
    //    }

    @Override
    public Optional<Vehicle> handle(CreateVehicleCommand command, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Vehicle vehicle = new Vehicle(command);
        vehicle.setUser(user);  // Asignar el vehículo al usuario

        return Optional.of(vehicleRepository.save(vehicle));
    }

    @Override
    public Optional<Vehicle> handle(UpdateVehicleCommand command) {
        validateUpdateVehicleCommand(command);
        var result = vehicleRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Vehicle does not exist");
        }
        var vehicleToUpdate = result.get();
        try {
            var updatedVehicle = vehicleRepository.save(vehicleToUpdate.updateInformation(command.marca(), command.modelo(), command.color(), command.placa()));
            return Optional.of(updatedVehicle);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating vehicle: " + e.getMessage());
        }
    }

    void validateUpdateVehicleCommand(UpdateVehicleCommand command) {
        if (command.id() == null) {
            throw new IllegalArgumentException("Vehicle id is required");
        }
        if (command.marca() == null) {
            throw new IllegalArgumentException("Vehicle marca is required");
        }
        if (command.modelo() == null) {
            throw new IllegalArgumentException("Vehicle modelo is required");
        }
        if (command.color() == null) {
            throw new IllegalArgumentException("Vehicle color is required");
        }
        if (command.placa() == null) {
            throw new IllegalArgumentException("Vehicle placa is required");
        }
    }

    public void updateVehicleCoordinates(Long vehicleId, double latitude, double longitude) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Actualizar las coordenadas del vehículo
        vehicle.updateCoordinates(latitude, longitude);

        // Guardar los cambios en el repositorio
        vehicleRepository.save(vehicle);
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
