package com.ionix.test.backend.repository;


import com.ionix.test.backend.model.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void prepareData(){
        List<UserEntity> userEntityList=new ArrayList<>();
        userEntityList.add(UserEntity.builder().id((long)1).name("juan perez").phone("9988")
                .email("email@email.cl").userName("pmunoz").build());
        userEntityList.add(UserEntity.builder().id((long)2).name("juan perez").phone("9988")
                .email("email2@email.cl").userName("pmunoz2").build());
        userEntityList.add(UserEntity.builder().id((long)3).name("juan perez").phone("9988")
                .email("email3@email.cl").userName("pmunoz").build());
        userEntityList.add(UserEntity.builder().id((long)4).name("juan perez").phone("9988")
                .email("email4@email.cl").userName("pmunoz").build());
        userRepository.saveAll(userEntityList);

    }

    @Test
    public void findByEmail_ok(){
        assertEquals(userRepository.findByEmail("email2@email.cl").get(0).getUserName(),"pmunoz2");
        assertEquals(userRepository.findByEmail("email2@email.cl").size(),1);
    }

}