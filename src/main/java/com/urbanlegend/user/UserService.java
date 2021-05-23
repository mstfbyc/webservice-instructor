package com.urbanlegend.user;

import com.urbanlegend.error.NotFoundException;
import com.urbanlegend.file.FileService;
import com.urbanlegend.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    FileService fileService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
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
        if(userUpdateVM.getImage()!=null){
            String oldImage = user.getImage();
            try {
                String storedFileName = fileService.writeBase64EncodedStringToFile(userUpdateVM.getImage());
                user.setImage(storedFileName);
                fileService.deleteProfileImage(oldImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        fileService.deleteAllStoredFilesForUser(user);
        userRepository.delete(user);
    }
}
