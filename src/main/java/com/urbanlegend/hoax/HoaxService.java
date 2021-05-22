package com.urbanlegend.hoax;

import com.urbanlegend.user.User;
import com.urbanlegend.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HoaxService {


    HoaxRepository hoaxRepository;
    UserService userService;


    public HoaxService(HoaxRepository hoaxRepository, UserService userService) {

        this.hoaxRepository = hoaxRepository;
        this.userService = userService;
    }

    public void save(Hoax hoax, User user) {
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> getHoaxes(Pageable page) {
        return hoaxRepository.findAll(page);
    }

    public Page<Hoax> getHoaxesOfUser(String username, Pageable page) {
        User inDB = userService.getUser(username);
        return hoaxRepository.findByUser(inDB, page);
    }

    public Page<Hoax> getOldHoaxes(long id, String username, Pageable page) {
        Specification<Hoax> specification = idLessThan(id);
        if(username != null) {
            User inDB = userService.getUser(username);
            specification = specification.and(userIs(inDB));
        }
        return hoaxRepository.findAll(specification, page);
    }

    public long getNewHoaxesCount(long id, String username) {
        Specification<Hoax> specification = idGreaterThan(id);
        if(username != null) {
            User inDB = userService.getUser(username);
            specification = specification.and(userIs(inDB));
        }
        return hoaxRepository.count(specification);
    }

    public List<Hoax> getNewHoaxes(long id, String username, Sort sort) {
        Specification<Hoax> specification = idGreaterThan(id);
        if(username != null) {
            User inDB = userService.getUser(username);
            specification = specification.and(userIs(inDB));
        }
        return hoaxRepository.findAll(specification, sort);
    }


    Specification<Hoax> idLessThan(Long id){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get("id"), id);
        };
    }

    Specification<Hoax> userIs(User user){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }

    Specification<Hoax> idGreaterThan(long id){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("id"), id);
        };
    }

}
