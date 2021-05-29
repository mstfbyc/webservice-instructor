package com.urbanlegend.auth;

import com.urbanlegend.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Token {

    @Id
    private String token;

    @ManyToOne
    private User user;

}