package com.urbanlegend.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name="displayname")
    private String displayName;
    @Column(name = "password")
    private String password;
}
