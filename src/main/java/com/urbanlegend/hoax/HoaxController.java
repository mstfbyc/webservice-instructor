package com.urbanlegend.hoax;

import com.urbanlegend.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("hoaxes")
    ResponseEntity<GenericResponse> saveHoax(@RequestBody Hoax hoax){
        hoaxService.save(hoax);
        return ResponseEntity.ok(new GenericResponse("Hoax başarı ile kaydedildi."));
    }
}
