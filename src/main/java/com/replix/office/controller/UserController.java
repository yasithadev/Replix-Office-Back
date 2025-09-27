package com.replix.office.controller;

import com.replix.office.dtos.UserDTO;
import com.replix.office.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserManagementService userManagementService;
    @PostMapping("create")
    @PreAuthorize("hasAuthority('CREAT_USER')")
    public String createUser(@Valid @RequestBody UserDTO userDTO){
        System.out.println(userDTO.toString());
        userManagementService.createUser(userDTO);
        return "sucess with CREAT_USER";
    }
}
