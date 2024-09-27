package com.securecar.safedrive.notifications.infraestructure.persistence.jpa;

import com.securecar.safedrive.notifications.domain.model.aggregates.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}