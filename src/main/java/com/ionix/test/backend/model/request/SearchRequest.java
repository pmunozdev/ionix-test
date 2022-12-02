package com.ionix.test.backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest implements Serializable {


    private static final long serialVersionUID = 3191331416099759395L;
    private String parametro;

}
