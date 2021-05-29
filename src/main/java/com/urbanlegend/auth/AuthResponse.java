package com.urbanlegend.auth;

import com.urbanlegend.user.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserVM user;
}
