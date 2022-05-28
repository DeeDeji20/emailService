package com.africa.semicolon.emailService.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Builder
@Document("User")
public class User {
    @Email @NotNull @Id
    private String email;
    @NotNull
    private String password;
//    @DBRef
    private List<Notifications> notificationList = new ArrayList<>();
    private boolean isLoggedIn;
}
