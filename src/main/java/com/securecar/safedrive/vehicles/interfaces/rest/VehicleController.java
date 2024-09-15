package com.securecar.safedrive.vehicles.interfaces.rest;

import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import com.securecar.safedrive.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.securecar.safedrive.vehicles.domain.services.VehicleCommandService;
import com.securecar.safedrive.vehicles.domain.services.VehicleQueryService;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.CreateVehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.UpdateVehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.VehicleResource;
import com.securecar.safedrive.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.securecar.safedrive.vehicles.interfaces.rest.transform.UpdateVehicleCommandFromResourceAssembler;
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

    @PutMapping("{vehicleId}")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable Long vehicleId, @RequestBody UpdateVehicleResource resource){
        var updateVehicleCommand = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(vehicleId, resource);
        var updatedVehicle = vehicleCommandService.handle(updateVehicleCommand);
        if (updatedVehicle.isEmpty()) return ResponseEntity.badRequest().build();
        var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(updatedVehicle.get());
        return ResponseEntity.ok(vehicleResource);
    }
}
