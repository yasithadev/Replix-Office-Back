package com.replix.office.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user")
public class UserController {
    @PostMapping("create")
    @PreAuthorize("hasAuthority('READ_USER')")
    public String createUser(){
        return "sucess";
    }
}
