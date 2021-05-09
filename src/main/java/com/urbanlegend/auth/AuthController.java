package com.urbanlegend.auth;

import com.urbanlegend.error.ApiError;
import com.urbanlegend.shared.CurrentUser;
import com.urbanlegend.user.User;
import com.urbanlegend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/1.0/auth")
    public ResponseEntity<?> handleAuthentication(@CurrentUser User user){
        return ResponseEntity.ok(user);
    }
}
