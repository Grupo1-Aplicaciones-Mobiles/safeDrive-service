package com.securecar.safedrive.notifications.interfaces.rest.transform;


import com.securecar.safedrive.notifications.domain.model.aggregates.Notification;
import com.securecar.safedrive.notifications.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {

    public static NotificationResource toResource(Notification notification) {
        return new NotificationResource(notification.getUserId(), notification.getMessage(), notification.getDateTime());
    }
}