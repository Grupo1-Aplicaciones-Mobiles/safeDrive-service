package com.securecar.safedrive.notifications.interfaces.rest.resources;

import com.securecar.safedrive.notifications.domain.model.aggregates.Notification;

import java.time.LocalDateTime;

public record NotificationResource(Long userId, String message, LocalDateTime dateTime) {

    public static NotificationResource fromEntity(Notification notification) {
        return new NotificationResource(notification.getUserId(), notification.getMessage(), notification.getDateTime());
    }
}
