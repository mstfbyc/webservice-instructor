package com.urbanlegend.auth;

import com.urbanlegend.error.ApiError;
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

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/api/1.0/auth")
    public ResponseEntity<?> handleAuthentication(@RequestHeader(name="Authorization", required = false) String authorization){
        if(authorization == null){
            ApiError apiError = new ApiError(401,"Unauthorized request","/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }
        String base64encoded = authorization.split("Basic ")[1];
        String decoded = new String( Base64.getDecoder().decode(base64encoded));
        String[] parts = decoded.split(":");
        String username = parts[0];
        String password = parts[1];

        User checkUser = userRepository.findByUsername(username);
        if(checkUser ==null){
            ApiError apiError = new ApiError(401,"Unauthorized request","/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }
        String hashedPassword = checkUser.getPassword();
        if(!passwordEncoder.matches(password,hashedPassword)){
            ApiError apiError = new ApiError(401,"Unauthorized request","/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }
/*        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("username", checkUser.getUsername());
        responseBody.put("displayName",checkUser.getDisplayName());
        responseBody.put("images",checkUser.getImage());*/
        return ResponseEntity.ok(checkUser);

    }
}
