package com.example.orderdemo.dto;

import com.example.orderdemo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private Set<Role> roles;

}
