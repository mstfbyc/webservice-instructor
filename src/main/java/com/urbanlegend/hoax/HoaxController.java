package com.urbanlegend.hoax;

import com.urbanlegend.hoax.vm.HoaxVM;
import com.urbanlegend.shared.CurrentUser;
import com.urbanlegend.shared.GenericResponse;
import com.urbanlegend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    ResponseEntity<GenericResponse> saveHoax(@RequestBody Hoax hoax, @CurrentUser User user){
        hoaxService.save(hoax,user);
        return ResponseEntity.ok(new GenericResponse("Hoax başarı ile kaydedildi."));
    }

    @GetMapping("/hoaxes")
    ResponseEntity<Page<HoaxVM>> getHoax(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page){
        return ResponseEntity.ok(hoaxService.getHoaxes(page).map(HoaxVM::new));
    }

    @GetMapping("/users/{username}/hoaxes")
    ResponseEntity<Page<HoaxVM>> getUserHoaxes(@PathVariable String username, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page){
        return ResponseEntity.ok(hoaxService.getHoaxesOfUser(username, page).map(HoaxVM::new));
    }
}
