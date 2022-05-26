package com.africa.semicolon.emailService.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {
    @Id
    private String id;
    private String senderEmail;
    private String title;
    private String message;
    private boolean isRead;

    public Notifications(String senderEmail, String title, String message) {
        this.senderEmail = senderEmail;
        this.title = title;
        this.message = message;
    }
}
