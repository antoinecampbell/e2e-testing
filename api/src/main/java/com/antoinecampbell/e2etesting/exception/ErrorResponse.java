package com.antoinecampbell.e2etesting.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object meta;
}
