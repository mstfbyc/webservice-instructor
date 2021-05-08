package com.urbanlegend.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/secured")
    public String dummyRequest(){
        return "secured place";
    }
}
