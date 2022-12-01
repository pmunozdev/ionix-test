package com.ionix.test.backend.controller;

import com.ionix.test.backend.model.common.UserDTO;
import com.ionix.test.backend.model.request.SearchRequest;
import com.ionix.test.backend.model.request.UserRequestDTO;
import com.ionix.test.backend.model.response.SearchResponse;
import com.ionix.test.backend.service.SearchService;
import com.ionix.test.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/ionix/v1/search")
public class SearchApiController {


    @Autowired
    private SearchService SearchService;


    @PostMapping("/")
    @Operation(summary = "Servicio que consume la API para la prueba técnica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Api consultada ok", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en los parametros de entrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content),
            })
    public ResponseEntity<SearchResponse> consumeApiSearch(
            @Valid @RequestBody SearchRequest search) {
        return new ResponseEntity<>(SearchService.consultaApiSearch(search.getParametro()),HttpStatus.CREATED);
    }




}
