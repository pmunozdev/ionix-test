package com.ionix.test.backend.controller;

import com.ionix.test.backend.model.common.UserDTO;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/ionix/v1/user")
public class UserController {

    @PostMapping("/")
    public ResponseEntity crearUsuario(
            @RequestBody UserDTO user) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
