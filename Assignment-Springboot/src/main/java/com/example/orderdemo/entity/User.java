package com.example.orderdemo.entity;

import com.example.orderdemo.entity.enums.ProductSimpleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User{
    @Id
    private String id;
    private String fullName;
    private String phone;
    private String email;
    private ProductSimpleStatus status;
}