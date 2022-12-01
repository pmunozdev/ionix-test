package com.ionix.test.backend.service;

import com.ionix.test.backend.exception.MappingExceptionEntity;
import com.ionix.test.backend.model.common.UserDTO;
import com.ionix.test.backend.model.entity.UserEntity;
import com.ionix.test.backend.model.request.UserRequestDTO;
import com.ionix.test.backend.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO saveUser(UserRequestDTO user) {
        UserEntity userEntity=modelMapper.map(user, UserEntity.class);
        userEntity=userRepo.save(userEntity);
        try {
            return modelMapper.map(userEntity, UserDTO.class);
        }catch(MappingException e){
            throw new MappingExceptionEntity("saveUser - error el mapear la entidad user",e);
        }
    }

    @Override
    public List<UserDTO> listAllUser() {
        List<UserEntity> userEntity= userRepo.findAll();
        try {
        return userEntity.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        }catch(MappingException e){
            throw new MappingExceptionEntity("listAllUser - error el mapear la entidad user",e);
        }
    }

    @Override
    public List<UserDTO> findUserByEmail(String email) {
        List<UserEntity> userEntity= userRepo.findByEmail(email);
        try {
            return userEntity.stream().map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        }catch(MappingException e){
            throw new MappingExceptionEntity("findUserByEmail - error el mapear la entidad user",e);
        }
    }

    @Override
    public void deteleUserById(Long id) {
       userRepo.deleteById(id);
    }
}
