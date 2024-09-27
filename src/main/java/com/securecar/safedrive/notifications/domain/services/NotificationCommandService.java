package com.securecar.safedrive.notifications.domain.services;

import com.securecar.safedrive.notifications.domain.model.aggregates.Notification;
import com.securecar.safedrive.notifications.domain.model.commands.CreateNotificationCommand;

public interface NotificationCommandService {
    Notification createNotification(CreateNotificationCommand command);
}