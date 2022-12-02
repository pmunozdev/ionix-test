package com.ionix.test.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchApiResultResponse implements Serializable {


    private static final long serialVersionUID = 4639184562594841628L;
    List<SearchApiItemsResponse> items;
}
