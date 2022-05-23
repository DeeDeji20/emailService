package com.africa.semicolon.emailService.dtos;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class
CreateMessageDTO {
    private String sender;
    private String receiver;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private String msgBody;

    public CreateMessageDTO(String sender, String receiver, String msgBody) {
        this.sender = sender;
        this.receiver = receiver;
        this.msgBody = msgBody;
    }
}
