package com.ionix.test.backend.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorBody {

    private Integer code;
    private String message;
    private String detailedMessage;
    private LocalDateTime timestamp;

}
