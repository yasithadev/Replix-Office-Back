package com.replix.office.controller;

import com.replix.office.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.replix.office.models.SignInRequestDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private AuthService authService;

    @GetMapping("xx")
    public String Test() {
        return "Hi";
    }

    @PostMapping("signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequestDto signInRequestDto){
        System.out.println("signInRequestDto " + signInRequestDto);
        return authService.signInUser(signInRequestDto);
    }

}
