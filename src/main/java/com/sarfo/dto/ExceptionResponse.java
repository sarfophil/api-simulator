package com.sarfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExceptionResponse {
    @JsonProperty(value = "status")
    private String status;
    @JsonProperty(value = "message")
    private String message;
}
