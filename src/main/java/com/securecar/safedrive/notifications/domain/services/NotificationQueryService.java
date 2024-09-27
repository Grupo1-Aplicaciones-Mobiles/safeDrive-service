package com.securecar.safedrive.notifications.domain.services;

import com.securecar.safedrive.notifications.interfaces.rest.resources.NotificationResource;

import java.util.List;

public interface NotificationQueryService {
    List<NotificationResource> getNotificationsByUserId(Long userId);
}