package com.urbanlegend.configuration;


import com.urbanlegend.user.User;
import com.urbanlegend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User checkUser = userRepository.findByUsername(username);
        if(checkUser ==null){
           throw new UsernameNotFoundException("User not Found");
        }
        return new UrbanLegendsUserDetails(checkUser);
    }
}
