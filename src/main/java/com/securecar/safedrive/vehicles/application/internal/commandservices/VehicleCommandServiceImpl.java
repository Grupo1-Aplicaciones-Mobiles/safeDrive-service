package com.securecar.safedrive.vehicles.application.internal.commandservices;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.commands.CreateVehicleCommand;
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
}
