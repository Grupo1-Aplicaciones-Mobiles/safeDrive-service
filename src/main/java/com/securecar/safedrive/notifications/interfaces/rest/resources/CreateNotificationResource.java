package com.securecar.safedrive.notifications.interfaces.rest.resources;

public record CreateNotificationResource(Long userId, String message) {
    public CreateNotificationResource {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }
    }
}
