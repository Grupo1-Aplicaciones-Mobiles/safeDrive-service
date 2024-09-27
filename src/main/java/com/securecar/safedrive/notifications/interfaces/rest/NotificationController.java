package com.securecar.safedrive.notifications.interfaces.rest;

import com.securecar.safedrive.notifications.domain.model.commands.CreateNotificationCommand;
import com.securecar.safedrive.notifications.domain.services.NotificationCommandService;
import com.securecar.safedrive.notifications.domain.services.NotificationQueryService;
import com.securecar.safedrive.notifications.interfaces.rest.resources.CreateNotificationResource;
import com.securecar.safedrive.notifications.interfaces.rest.resources.NotificationResource;
import com.securecar.safedrive.tracking.application.internal.DistanceCalculatorService;
import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.securecar.safedrive.vehicles.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationCommandService commandService;

    @Autowired
    private NotificationQueryService queryService;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * Endpoint para crear una nueva notificación.
     *
     * @param resource el recurso que contiene los datos de la notificación a crear.
     * @return una respuesta HTTP indicando el resultado de la operación.
     */
    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody CreateNotificationResource resource) {
        CreateNotificationCommand command = new CreateNotificationCommand(resource.userId(), resource.message());
        commandService.createNotification(command);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para obtener las notificaciones de un usuario específico.
     *
     * @param userId el ID del usuario.
     * @return una lista de notificaciones para el usuario especificado.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResource>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationResource> notifications = queryService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }


    /**
     * Endpoint para crear una alerta de distancia entre el usuario y su vehículo.
     *
     * @param userId el ID del usuario.
     * @param vehicleId el ID del vehículo.
     * @return un mensaje indicando el estado de la alerta.
     */
    @PostMapping("/distance-alert")
    public ResponseEntity<String> createDistanceAlert(@RequestParam Long userId, @RequestParam Long vehicleId) {
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            var vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));

            String distanceInfo = distanceCalculatorService.calculateDistanceAndTime(user.getCoordinates(), vehicle.getCoordinates());

            String message;
            if (distanceInfo.contains("km") && Double.parseDouble(distanceInfo.split(" ")[1]) > 2.0) {
                message = "Te encuentras lejos de tu vehículo.";
            } else {
                message = "Estás cerca de tu vehículo.";
            }

            // Crear la notificación
            CreateNotificationCommand command = new CreateNotificationCommand(userId, message);
            commandService.createNotification(command);

            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear la alerta de distancia: " + e.getMessage());
        }
    }
}
