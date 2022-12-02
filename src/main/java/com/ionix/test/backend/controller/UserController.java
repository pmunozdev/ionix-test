package com.ionix.test.backend.controller;

import com.ionix.test.backend.model.common.UserDTO;
import com.ionix.test.backend.model.request.UserRequestDTO;
import com.ionix.test.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/ionix/v1/")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user")
    @Operation(summary = "Servicio para la creación de usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "409", description = "El usuario ya existe", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en los parametros de entrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            })
    public ResponseEntity<UserDTO> crearUsuario(
            @Valid @RequestBody UserRequestDTO user) {
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }


    @GetMapping("/users")
    @Operation(summary = "Servicio que lista los usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "usuario no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            })
    public ResponseEntity<List<UserDTO>> listarUsuario() {
        List<UserDTO> users=userService.listAllUser();
        if(users.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping ("/users/by-email/{email}")
    @Operation(summary = "Servicio que busca un usuarios por email")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "usuario no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en los parametros de entrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            })
    public ResponseEntity<List<UserDTO>> buscarUsuarioEmail(@PathVariable("email") String email) {
        List<UserDTO> users=userService.findUserByEmail(email);
        if(users.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }


    @DeleteMapping("user/{id}")
    @Operation(summary = "Elimina un usuario por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuarios eliminado", content = @Content),
                    @ApiResponse(responseCode = "204", description = "usuario no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en los parametros de entrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            })
    public ResponseEntity<Void> eliminarUsuario(@PathVariable(value="id") Long id) {
        if(!userService.deteleUserById(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
