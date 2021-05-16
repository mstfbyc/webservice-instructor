package com.urbanlegend.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user){
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Page<User> allUsers(Pageable pageable) {
        //Pageable pageable = PageRequest.of(0,5);
        Page<User> response = userRepository.findAll(pageable);
        return response;
    }

    public Page<UserProjection> allUsersProjection(Pageable pageable) {
        //Pageable pageable = PageRequest.of(0,5);
        Page<UserProjection> response = userRepository.getAllUsersProjection(pageable);
        return response;
    }

    public Page<User> allUsers(User user,Pageable pageable) {
        Page<User> response;
        if(user !=null){
            response = userRepository.findByUsernameNot(user.getUsername(), pageable);
        }else{
            response = userRepository.findAll(pageable);
        }
        return response;
    }


}
