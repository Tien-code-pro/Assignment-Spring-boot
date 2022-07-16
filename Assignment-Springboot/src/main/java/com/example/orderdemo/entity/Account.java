package com.example.orderdemo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username; // select * from account where username = "username"-> salt, passwordhash, passwordHash
    private String password; // đã mã hoá. salt+passwordhash (md5, sha)
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "accounts_roles",
            joinColumns = @JoinColumn(
                    name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
    private Date createdAt;
    private Date updatedAt;
    private int status;
    private String verifyCode;
}
