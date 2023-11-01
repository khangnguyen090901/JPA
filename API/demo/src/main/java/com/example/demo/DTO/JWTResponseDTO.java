package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public JWTResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
