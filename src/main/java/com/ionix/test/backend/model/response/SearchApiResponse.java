package com.ionix.test.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchApiResponse implements Serializable {

    private static final long serialVersionUID = 6521726077377997727L;
    private Integer responseCode;
    private String description;
    private SearchApiResultResponse result;
}
