package com.securecar.safedrive.notifications.domain.model.aggregates;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long idNotification;

    @NotBlank
    @Column
    private Long userId;

    @NotBlank
    @Size(max = 255)
    @Column
    private String message;

    @Column
    private LocalDateTime dateTime;

    public Notification(Long userId, String message) {
        this.userId = userId;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }


    public Notification() {
    }

    public void updateMessage(String newMessage) {
        this.message = newMessage;
    }
}