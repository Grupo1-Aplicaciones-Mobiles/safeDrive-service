package com.securecar.safedrive.notifications.application.internal.commandservices;

import com.securecar.safedrive.notifications.domain.model.aggregates.Notification;
import com.securecar.safedrive.notifications.domain.model.commands.CreateNotificationCommand;
import com.securecar.safedrive.notifications.domain.services.NotificationCommandService;
import com.securecar.safedrive.notifications.infraestructure.persistence.jpa.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(CreateNotificationCommand command) {
        Notification notification = new Notification(command.userId(), command.message());
        return notificationRepository.save(notification);
    }
}