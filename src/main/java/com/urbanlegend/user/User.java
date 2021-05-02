package com.urbanlegend.user;


import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @NotNull
    @Column(name = "username")
    @Size(min = 4, max = 30)
    //@UniqueUsername
    private String username;

    @NotNull
    @Column(name="displayname")
    @Size(min = 4, max = 30)
    private String displayName;

    @Column(name = "password")
    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    private String password;
}
