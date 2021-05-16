package com.urbanlegend.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.urbanlegend.shared.CurrentUser;
import com.urbanlegend.shared.GenericResponse;
import com.urbanlegend.shared.Views;
import com.urbanlegend.user.vm.UserVM;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;



@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creat User", notes = "Urban legends user sign up")
    public GenericResponse createUser(@Valid @RequestBody User user){
        userService.saveUser(user);
        GenericResponse response = new GenericResponse();
        response.setMessage("User Created");
        return response;

    }

    @GetMapping("/api/1.0/users")
    @JsonView(Views.Base.class)
    @ApiOperation(value = "Get all users")
    public  ResponseEntity<Page<User>> allusers(Pageable pageable){
        Page<User> userList = userService.allUsers(pageable);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/api/1.0/usersProjection")
    @ApiOperation(value = "Get all users")
    public  ResponseEntity<Page<UserProjection>> allUsersProjection(Pageable pageable){
        Page<UserProjection> userList = userService.allUsersProjection(pageable);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/api/1.0/usersVM")
    @ApiOperation(value = "Get all users")
    public  ResponseEntity<Page<UserVM>> allusersVM(Pageable pageable){
        Page<UserVM> userList = userService.allUsers(pageable).map(UserVM::new);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/api/1.0/usersVM")
    @ApiOperation(value = "Get all users")
    public  ResponseEntity<Page<UserVM>> allusersVMCurrentUser(Pageable pageable, @CurrentUser User user){
        Page<UserVM> userList = userService.allUsers(user,pageable).map(UserVM::new);
        return ResponseEntity.ok(userList);
    }
}
