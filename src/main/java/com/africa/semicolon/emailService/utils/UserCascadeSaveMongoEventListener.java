//package com.africa.semicolon.emailService.utils;
//
//import com.africa.semicolon.emailService.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
//import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
//
//public class UserCascadeSaveMongoEventListener extends AbstractMongoEventListener {
//        @Autowired
//        private MongoOperations mongoOperations;
//
//    @Bean
//    public UserCascadeSaveMongoEventListener userCascadingMongoEventListener() {
//        return new UserCascadeSaveMongoEventListener();
//    }
//}
