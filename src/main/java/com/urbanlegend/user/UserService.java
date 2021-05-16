package com.urbanlegend.user;

import com.urbanlegend.error.NotFoundException;
import com.urbanlegend.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Page<User> allUsers(User user,Pageable pageable) {
        Page<User> response;
        if(user !=null){
            response = userRepository.findByUsernameNot(user.getUsername(), pageable);
        }else{
            response = userRepository.findAll(pageable);
        }
        return response;
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new NotFoundException();
        }
        return user;
    }

    public User updateUser(String username, UserUpdateVM userUpdateVM) {
        User user = getUser(username);
        user.setDisplayName(userUpdateVM.getDisplayName());
        return userRepository.save(user);
    }
}
