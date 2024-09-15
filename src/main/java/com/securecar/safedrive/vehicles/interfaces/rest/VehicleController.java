package com.securecar.safedrive.vehicles.interfaces.rest;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.commands.DeleteVehicleCommand;
import com.securecar.safedrive.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.securecar.safedrive.vehicles.domain.services.VehicleCommandService;
import com.securecar.safedrive.vehicles.domain.services.VehicleQueryService;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.CreateVehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.VehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.securecar.safedrive.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;

    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    @PostMapping
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource resource){
        Optional<Vehicle> vehicle = vehicleCommandService
                .handle(CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource));
        return vehicle.map(source -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(source),CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long id){
        Optional<Vehicle> vehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(id));
        return vehicle.map(source -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(source)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        try {
            vehicleCommandService.handle(new DeleteVehicleCommand(id));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
