package com.ionix.test.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultResponse implements Serializable {
    private static final long serialVersionUID = 8334415572741505708L;
    private Integer registerCount;
}
