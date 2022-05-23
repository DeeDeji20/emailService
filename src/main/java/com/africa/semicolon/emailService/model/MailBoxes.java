package com.africa.semicolon.emailService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
//@NoArgsConstructor
@Document("MailBoxes")
public class MailBoxes {
    @Id
    private String email;
    private List<MailBox> mailboxes;

    public MailBoxes() {
        this.mailboxes = new ArrayList<>();
    }


}
