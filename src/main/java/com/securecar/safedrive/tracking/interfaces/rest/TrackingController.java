package com.securecar.safedrive.tracking.interfaces.rest;

import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.securecar.safedrive.tracking.application.internal.DistanceCalculatorService;
import com.securecar.safedrive.vehicles.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final DistanceCalculatorService distanceCalculatorService;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public TrackingController(DistanceCalculatorService distanceCalculatorService, UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.distanceCalculatorService = distanceCalculatorService;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Endpoint para calcular la distancia entre el usuario y su vehículo usando Google Distance Matrix API
     * @param userId el ID del usuario
     * @param vehicleId el ID del vehículo
     * @return la distancia en formato legible (ej: "5.2 km")
     */
    @GetMapping("/distance")
    public ResponseEntity<String> calculateDistanceAndTime(@RequestParam Long userId, @RequestParam Long vehicleId) throws Exception {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        var vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        String result = distanceCalculatorService.calculateDistanceAndTime(user.getCoordinates(), vehicle.getCoordinates());

        return ResponseEntity.ok(result);
    }
}