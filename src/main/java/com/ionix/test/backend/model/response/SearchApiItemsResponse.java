package com.ionix.test.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchApiItemsResponse implements Serializable {

    private static final long serialVersionUID = 2363771498227436853L;
    private String name;
    private SearchApiItemsDetailResponse detail;
}
