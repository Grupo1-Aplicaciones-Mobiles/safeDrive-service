package com.securecar.safedrive.vehicles.interfaces.rest.transform;

import com.securecar.safedrive.vehicles.domain.model.commands.CreateVehicleCommand;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource) {
        return new CreateVehicleCommand(resource.marca(), resource.modelo(), resource.color(), resource.placa());
    }
}
