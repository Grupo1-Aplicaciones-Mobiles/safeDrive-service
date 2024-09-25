package com.securecar.safedrive.vehicles.interfaces.rest.transform;

import com.securecar.safedrive.vehicles.domain.model.commands.UpdateVehicleCommand;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.UpdateVehicleResource;

public class UpdateVehicleCommandFromResourceAssembler {
    public static UpdateVehicleCommand toCommandFromResource(Long id, UpdateVehicleResource resource) {
        return new UpdateVehicleCommand(
            id,
            resource.marca(),
            resource.modelo(),
            resource.color(),
            resource.placa()
        );
    }
}
