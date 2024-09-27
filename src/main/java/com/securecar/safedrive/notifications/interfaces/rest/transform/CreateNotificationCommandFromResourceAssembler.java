package com.securecar.safedrive.notifications.interfaces.rest.transform;

import com.securecar.safedrive.notifications.domain.model.commands.CreateNotificationCommand;
import com.securecar.safedrive.notifications.interfaces.rest.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {

    public static CreateNotificationCommand toCommand(CreateNotificationResource resource) {
        return new CreateNotificationCommand(resource.userId(), resource.message());
    }
}