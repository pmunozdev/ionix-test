package com.ionix.test.backend.service;


import com.ionix.test.backend.exception.EncodeException;
import com.ionix.test.backend.exception.MappingExceptionEntity;
import com.ionix.test.backend.model.common.UserDTO;
import com.ionix.test.backend.model.entity.UserEntity;
import com.ionix.test.backend.model.request.UserRequestDTO;
import com.ionix.test.backend.model.response.SearchApiResponse;
import com.ionix.test.backend.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void usersave_ok(){
        String validtestString="test ok";
        Mockito.when(userRepository.save(any(UserEntity.class)))
                .thenReturn(new UserEntity());
        Mockito.when(modelMapper.map(any(UserRequestDTO.class),any(Class.class)))
                .thenReturn(new UserEntity());
        Mockito.when(modelMapper.map(any(UserEntity.class),any(Class.class)))
                .thenReturn(UserDTO.builder().userName(validtestString).build());
        assertEquals(userServiceImpl.saveUser(new UserRequestDTO()).getUserName(),validtestString);

    }

    @Test(expected = MappingExceptionEntity.class)
    public void usersave_MappingException(){
        Mockito.when(userRepository.save(any(UserEntity.class)))
                .thenReturn(new UserEntity());
        Mockito.when(modelMapper.map(any(UserRequestDTO.class),any(Class.class)))
                .thenReturn(new UserEntity());
        Mockito.when(modelMapper.map(any(UserEntity.class),any(Class.class)))
                        .thenThrow(new MappingException(Collections.singletonList(new ErrorMessage("error"))));
        userServiceImpl.saveUser(new UserRequestDTO());
    }


    @Test
    public void listAllUser_ok(){
        String validtestString="test ok";
        Mockito.when(userRepository.findAll())
                .thenReturn(Arrays.asList(new UserEntity(),new UserEntity()));
        Mockito.when(modelMapper.map(any(UserEntity.class),any(Class.class)))
                .thenReturn(UserDTO.builder().userName(validtestString).build());
        assertEquals(userServiceImpl.listAllUser().size(),2);

    }

    @Test(expected = MappingExceptionEntity.class)
    public void listAllUser_MappingException(){
        Mockito.when(userRepository.findAll())
                .thenReturn(Arrays.asList(new UserEntity(),new UserEntity()));
        Mockito.when(modelMapper.map(any(UserEntity.class),any(Class.class)))
                .thenThrow(new MappingException(Collections.singletonList(new ErrorMessage("error"))));
        userServiceImpl.listAllUser();

    }

    @Test
    public void findUserByEmail_ok(){
        String validtestString="test ok";
        Mockito.when(userRepository.findByEmail(anyString()))
                .thenReturn(Collections.singletonList(new UserEntity()));
        Mockito.when(modelMapper.map(any(UserEntity.class),any(Class.class)))
                .thenReturn(UserDTO.builder().userName(validtestString).build());
        assertEquals(userServiceImpl.findUserByEmail("test").size(),1);
        assertEquals(userServiceImpl.findUserByEmail("test").get(0).getUserName(),validtestString);

    }

    @Test(expected = MappingExceptionEntity.class)
    public void findUserByEmail_MappingException(){
        Mockito.when(userRepository.findByEmail(anyString()))
                .thenReturn(Collections.singletonList(new UserEntity()));
        Mockito.when(modelMapper.map(any(UserEntity.class),any(Class.class)))
                .thenThrow(new MappingException(Collections.singletonList(new ErrorMessage("error"))));
        userServiceImpl.findUserByEmail("test");
    }

    @Test
    public void deteleUserById_true(){
        String validtestString="test ok";
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(false);
        Mockito.doNothing().when(userRepository).deleteById(anyLong());
        assertEquals(userServiceImpl.deteleUserById(1L),false);
    }

    @Test
    public void deteleUserById_false(){
        String validtestString="test ok";
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(anyLong());
        assertEquals(userServiceImpl.deteleUserById(1L),true);
    }



}