package com.ionix.test.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResponse implements Serializable {


    private static final long serialVersionUID = -798714817747145536L;
    private Integer responseCode;
    private String description;
    private Long elapsedTime;
    private SearchResultResponse result;

}
