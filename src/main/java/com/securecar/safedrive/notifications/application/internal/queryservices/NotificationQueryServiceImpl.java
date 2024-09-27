package com.securecar.safedrive.notifications.application.internal.queryservices;

import com.securecar.safedrive.notifications.domain.services.NotificationQueryService;
import com.securecar.safedrive.notifications.infraestructure.persistence.jpa.NotificationRepository;
import com.securecar.safedrive.notifications.interfaces.rest.resources.NotificationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationResource> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(NotificationResource::fromEntity)
                .collect(Collectors.toList());
    }
}