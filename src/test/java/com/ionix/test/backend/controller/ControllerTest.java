package com.ionix.test.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionix.test.backend.model.common.UserDTO;
import com.ionix.test.backend.model.request.SearchRequest;
import com.ionix.test.backend.model.request.UserRequestDTO;
import com.ionix.test.backend.model.response.SearchResponse;
import com.ionix.test.backend.service.SearchService;
import com.ionix.test.backend.service.UserService;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService SearchService;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String URL_SERVICE = "/ionix/v1/";



    @SneakyThrows
    @Test
    public void consumeApiSearch_ok()  {
        given(SearchService.getApiSearch(anyString())).willReturn(SearchResponse.builder().responseCode(200).build());
        String json = objectMapper.writeValueAsString(new SearchRequest("parametro"));
        this.mockMvc.perform(post(URL_SERVICE+"search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", Matchers.equalTo(200)));
    }

    @SneakyThrows
    @Test
    public void crearUsuario_ok()  {
        UserRequestDTO user=UserRequestDTO.builder()
                .name("name")
                .phone("phone")
                .email("email")
                .userName("username").build();
        given(userService.saveUser(any(UserRequestDTO.class))).willReturn(UserDTO.builder().id("1").build());
        String json = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post(URL_SERVICE+"user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo("1")));
    }


    @SneakyThrows
    @Test
    public void consultaListaUsuarios_ok()  {
        given(userService.listAllUser()).willReturn(Collections.singletonList(UserDTO.builder().id("1").build()));
        this.mockMvc.perform(get(URL_SERVICE+"users")
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo("1")));
    }

    @SneakyThrows
    @Test
    public void consultaListaUsuarios_size0()  {
        given(userService.listAllUser()).willReturn(new ArrayList<>());
        this.mockMvc.perform(get(URL_SERVICE+"users")
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());
    }


    @SneakyThrows
    @Test
    public void consultaUsuarios_byEmail_size0()  {
        given(userService.findUserByEmail(anyString())).willReturn(new ArrayList<>());
        this.mockMvc.perform(get(URL_SERVICE+"users?email=1")
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    public void consultaUsuarios_byEmail_ok()  {
        given(userService.findUserByEmail(anyString())).willReturn(Collections.singletonList(UserDTO.builder().id("1").build()));
        this.mockMvc.perform(get(URL_SERVICE+"users/by-email/1")
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo("1")));
    }

    @SneakyThrows
    @Test
    public void borrareUsuarios_ok()  {
        given(userService.deteleUserById(anyLong())).willReturn(true);
        this.mockMvc.perform(delete(URL_SERVICE+"user/1")
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

    }

    @SneakyThrows
    @Test
    public void borrareUsuarios_noContent()  {
        given(userService.deteleUserById(anyLong())).willReturn(false);
        this.mockMvc.perform(delete(URL_SERVICE+"user/1")
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());
    }



}