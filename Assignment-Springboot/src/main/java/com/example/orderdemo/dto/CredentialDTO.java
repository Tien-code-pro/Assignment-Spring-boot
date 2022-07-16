package com.example.orderdemo.dto;

import com.example.orderdemo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CredentialDTO {
    private String accessToken;
    private String refreshToken;
    private List<String> roles;

}
