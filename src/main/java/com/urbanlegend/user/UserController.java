package com.urbanlegend.user;


import com.urbanlegend.error.ApiError;
import com.urbanlegend.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse createUser(@Valid @RequestBody User user){
/*        Map<String,String> validationErrors = new HashMap<>();
        ApiError error = new ApiError(400,"Validation error","api/1.0/users");
        if(user.getUsername()==null || user.getUsername().isEmpty()){
            validationErrors.put("username","Username cannot be null");
        }
        if(user.getDisplayName() == null || user.getDisplayName().isEmpty()){
            validationErrors.put("displayName","displayName cannot be null");
        }
        if(validationErrors.size()>0){
            error.setValidationErrors(validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }*/
        userService.saveUser(user);
        GenericResponse response = new GenericResponse();
        response.setMessage("User Created");
        return response;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception){
        ApiError error = new ApiError(400,"Validation error","api/1.0/users");
        Map<String,String> validationErrors = new HashMap<>();
        for (FieldError fieldError: exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return error;
    }
}
