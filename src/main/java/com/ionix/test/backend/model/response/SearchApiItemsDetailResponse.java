package com.ionix.test.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchApiItemsDetailResponse implements Serializable {

    private static final long serialVersionUID = -6555520723335897619L;
    private String email;
    private String phoneNumber;
}
