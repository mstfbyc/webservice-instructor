package com.urbanlegend.hoax;

import com.urbanlegend.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Hoax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    private User user;

}
