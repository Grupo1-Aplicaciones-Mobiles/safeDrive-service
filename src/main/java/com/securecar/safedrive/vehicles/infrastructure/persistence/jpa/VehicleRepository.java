package com.securecar.safedrive.vehicles.infrastructure.persistence.jpa;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlaca(String placa);
}
