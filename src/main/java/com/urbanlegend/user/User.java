package com.urbanlegend.user;

import com.urbanlegend.auth.Token;
import com.urbanlegend.hoax.Hoax;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @NotNull(message = "{urbanlegend.constraint.username.NotNull.message}")
    @Column(name = "username" )
    @Size(min = 4, max = 30)
    @UniqueUsername(message ="{urbanlegend.constraint.username.UniqueUsername.message}" )
    private String username;

    @NotNull(message = "{urbanlegend.constraint.displayName.NotNull.message}")
    @Column(name="displayname")
    @Size(min = 4, max = 30)
    private String displayName;

    @Column(name = "password")
    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "{urbanlegend.constrain.password.Pattern.message}")
    private String password;

    @Column(name="image")
    private String image;

    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE)
    private List<Hoax> hoaxes;

    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE)
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_user");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}