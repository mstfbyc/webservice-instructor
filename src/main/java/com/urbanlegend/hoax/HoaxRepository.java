package com.urbanlegend.hoax;

import com.urbanlegend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaxRepository extends JpaRepository<Hoax,Long> {

    Page<Hoax> findByUser(User user, Pageable pageable);
}
