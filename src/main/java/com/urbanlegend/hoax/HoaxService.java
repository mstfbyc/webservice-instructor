package com.urbanlegend.hoax;

import com.urbanlegend.user.User;
import com.urbanlegend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class HoaxService {

    @Autowired
    HoaxRepository hoaxRepository;

    @Autowired
    UserService userService;

    public void save(Hoax hoax, User user) {
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> getHoaxes(Pageable page) {
        return hoaxRepository.findAll(page);
    }

    public Page<Hoax> getHoaxesOfUser(String username, Pageable page) {
        User user = userService.getUser(username);
        return hoaxRepository.findByUser(user,page);
    }
}
