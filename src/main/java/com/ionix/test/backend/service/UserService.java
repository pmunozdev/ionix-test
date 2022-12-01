package com.ionix.test.backend.service;

import com.ionix.test.backend.model.common.UserDTO;
import com.ionix.test.backend.model.request.UserRequestDTO;

import java.util.List;


public interface UserService {

    /**
     * Metodo que guarda un usuario
     * @param user {@link UserRequestDTO}
     */
    UserDTO saveUser(UserRequestDTO user);

    /**
     * Lista todos los usuarios
     * @return user {@link List<UserDTO>}
     */
    List<UserDTO> listAllUser();

    /**
     * Busca Usuarios por emails
     * @param email  {@link String}
     * @return  {@link UserDTO}
     */
    List<UserDTO> findUserByEmail(String email);

    /**
     * Elimina un usuario
     * @param id  {@link Long}
     */
    Boolean deteleUserById(Long id);


}
