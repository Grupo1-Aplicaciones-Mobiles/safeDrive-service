package com.securecar.safedrive.vehicles.application.internal.queryservices;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;
import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.queries.GetCoordinatesByVehicleIdQuery;
import com.securecar.safedrive.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.securecar.safedrive.vehicles.domain.services.VehicleQueryService;
import com.securecar.safedrive.vehicles.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {

    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;

    @Autowired
    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {

        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return vehicleRepository.findById(query.id());
    }

    @Override
    public List<Vehicle> getVehiclesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return vehicleRepository.findByUser(user);  // Buscar vehículos asociados al usuario
    }

    @Override
    public Optional<Coordinates> handle(GetCoordinatesByVehicleIdQuery query) {
        return vehicleRepository.findById(query.getVehicleId())
                .map(Vehicle::getCoordinates);
    }

}
