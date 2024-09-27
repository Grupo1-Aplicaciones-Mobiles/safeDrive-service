package com.securecar.safedrive.notifications.domain.model.commands;

public record CreateNotificationCommand(Long userId, String message) {
    public CreateNotificationCommand {
        if (userId == null)
            throw new IllegalArgumentException("User ID cannot be null");
        if (message == null || message.isBlank())
            throw new IllegalArgumentException("Message cannot be null or empty");
    }
}