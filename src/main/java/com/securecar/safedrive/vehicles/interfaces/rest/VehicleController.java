package com.securecar.safedrive.vehicles.interfaces.rest;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.securecar.safedrive.vehicles.domain.services.VehicleCommandService;
import com.securecar.safedrive.vehicles.domain.services.VehicleQueryService;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.CreateVehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.VehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.dtos.UpdateVehicleCoordinatesDTO;
import com.securecar.safedrive.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.securecar.safedrive.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import com.securecar.safedrive.vehicles.application.internal.commandservices.VehicleCommandServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;
    private final VehicleCommandServiceImpl vehicleCoordCommandService;

    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService, VehicleCommandServiceImpl vehicleCoordinateCommandService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
        this.vehicleCoordCommandService = vehicleCoordinateCommandService;
    }

    @PostMapping
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource resource, Principal principal){
        String username = principal.getName(); // Obtenemos el nombre del usuario autenticado
        Optional<Vehicle> vehicle = vehicleCommandService
                .handle(CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource), username);
        return vehicle.map(source -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(source),CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }

//    @GetMapping("{id}")
//    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long id){
//        Optional<Vehicle> vehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(id));
//        return vehicle.map(source -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(source)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @GetMapping
    public ResponseEntity<List<VehicleResource>> getVehiclesByUser(Principal principal) {
        String username = principal.getName(); // Obtenemos el nombre del usuario autenticado
        List<Vehicle> vehicles = vehicleQueryService.getVehiclesByUser(username);
        return ResponseEntity.ok(vehicles.stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    /**
     * Endpoint para actualizar las coordenadas del vehículo
     * @param updateVehicleCoordinatesDTO contiene el vehicleId, latitud y longitud
     * @return HTTP 200 OK si la actualización es exitosa
     */
    @PutMapping("/coordinates")
    public ResponseEntity<Void> updateVehicleCoordinates(@Valid @RequestBody UpdateVehicleCoordinatesDTO updateVehicleCoordinatesDTO) {
        vehicleCoordCommandService.updateVehicleCoordinates(
                updateVehicleCoordinatesDTO.getVehicleId(),
                updateVehicleCoordinatesDTO.getLatitude(),
                updateVehicleCoordinatesDTO.getLongitude()
        );

        return ResponseEntity.ok().build();
    }
}
