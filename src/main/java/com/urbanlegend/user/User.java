package com.urbanlegend.user;


import com.fasterxml.jackson.annotation.JsonView;
import com.urbanlegend.shared.Views;
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
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @NotNull(message = "{urbanlegend.constraint.username.NotNull.message}")
    @Column(name = "username" )
    @Size(min = 4, max = 30)
    @UniqueUsername(message ="{urbanlegend.constraint.username.UniqueUsername.message}" )
    @JsonView(Views.Base.class)
    private String username;

    @NotNull(message = "{urbanlegend.constraint.displayName.NotNull.message}")
    @Column(name="displayname")
    @Size(min = 4, max = 30)
    @JsonView(Views.Base.class)
    private String displayName;

    @Column(name = "password")
    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "{urbanlegend.constrain.password.Pattern.message}")
    private String password;

    @JsonView(Views.Base.class)
    @Column(name="image")
    private String image;
}
