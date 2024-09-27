package com.securecar.safedrive.vehicles.interfaces.rest.transform;

import com.securecar.safedrive.vehicles.domain.model.commands.DeleteVehicleCommand;
import com.securecar.safedrive.vehicles.interfaces.rest.resources.DeleteVehicleResource;

public class DeleteCommandFromResourceAssembler {
    public static DeleteVehicleCommand toCommandFromResource(DeleteVehicleResource resource) {
        return new DeleteVehicleCommand(resource.id());
    }
}
