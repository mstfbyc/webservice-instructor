package com.urbanlegend.user;

import com.urbanlegend.shared.CurrentUser;
import com.urbanlegend.shared.GenericResponse;
import com.urbanlegend.user.vm.UserUpdateVM;
import com.urbanlegend.user.vm.UserVM;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creat User", notes = "Urban legends user sign up")
    public GenericResponse createUser(@Valid @RequestBody User user){
        userService.saveUser(user);
        GenericResponse response = new GenericResponse();
        response.setMessage("User Created");
        return response;
    }
    @GetMapping("/users")
    @ApiOperation(value = "Get all users")
    public  ResponseEntity<Page<UserVM>> allusers(Pageable pageable, @CurrentUser User user){
        Page<UserVM> userList = userService.allUsers(user,pageable).map(UserVM::new);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users{username}")
    @ApiOperation(value = "Get all users")
    public  ResponseEntity<UserVM> user(@PathVariable String username){
        User user = userService.getUser(username);
        return ResponseEntity.ok(new UserVM(user));
    }

    @PutMapping("/users/{username}")
    @ApiOperation(value = "Update User", notes = "Urban legends update user ")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<?>  updateUser(@Valid @RequestBody UserUpdateVM userUpdateVM,@PathVariable String username){
        User user = userService.updateUser(username,userUpdateVM);
        return ResponseEntity.ok(new UserVM(user));
    }

    @DeleteMapping("/users/{username}")
    @PreAuthorize("#username == principal.username")
    GenericResponse deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new GenericResponse("User is removed");
    }
}
