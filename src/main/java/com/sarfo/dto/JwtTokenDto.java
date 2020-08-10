package com.sarfo.dto;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String token;
    public JwtTokenDto(String token){
        this.token = token;
    }
}
