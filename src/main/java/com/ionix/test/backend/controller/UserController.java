package com.ionix.test.backend.controller;

import com.ionix.test.backend.model.common.UserDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
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
@OpenAPIDefinition(info = @Info(title = "Servicio REST de prueba desarrollador backend", version = "1"))
public class UserController {

    @PostMapping("/")
    @Operation(summary = "Crear un usuario")
    public ResponseEntity crearUsuario(
            @RequestBody UserDTO user) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
