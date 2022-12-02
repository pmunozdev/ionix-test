package com.ionix.test.backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO implements Serializable {

    private static final long serialVersionUID = 7327259937407427316L;

    @NotEmpty(message = "name es requerido.")
    private String name;

    @NotEmpty(message = "userName es requerido.")
    private String userName;

    @NotEmpty(message = "email es requerido.")
    private String email;

    @NotEmpty(message = "phone es requerido.")
    private String phone;

}
