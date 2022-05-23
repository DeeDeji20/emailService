package com.africa.semicolon.emailService.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private String msgId;
    private String sender;
    private String receiver;
    private LocalDateTime localDateTime;
    private String msgBody;
}
