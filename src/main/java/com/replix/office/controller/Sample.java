package com.replix.office.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sample {
    @GetMapping("sayHello")
    @PreAuthorize("hasAuthority('READ_USER')")
    public String sayHello(){
        return "Hello";
    }

}
