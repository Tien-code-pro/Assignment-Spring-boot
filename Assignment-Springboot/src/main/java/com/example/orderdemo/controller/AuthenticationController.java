package com.example.orderdemo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.orderdemo.dto.AccountDTO;
import com.example.orderdemo.dto.CredentialDTO;
import com.example.orderdemo.dto.RegisterDTO;
import com.example.orderdemo.entity.Account;
import com.example.orderdemo.entity.Role;
import com.example.orderdemo.service.AuthenticationService;
import com.example.orderdemo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        AccountDTO account = authenticationService.saveAccount(registerDTO);
        return ResponseEntity.ok().body(account);
    }

    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("require token in header");
        }
        try {
            String token = authorizationHeader.replace("Bearer", "").trim();
            DecodedJWT decodedJWT = JwtUtil.getDecodedJwt(token);
            String username = decodedJWT.getSubject();
            //load account in the token
            Account account = authenticationService.getAccount(username);
            if (account == null) {
                return ResponseEntity.badRequest().body("Wrong token: Username not exist");
            }
            //now return new token
            //generate tokens
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<String> roles = new ArrayList<>();
            for (Role role:
                    account.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
                roles.add(role.getName());
            }

//            authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
            String accessToken = JwtUtil.generateToken(
                    account.getUsername(),
                    roles,
                    request.getRequestURL().toString(),
                    JwtUtil.ONE_DAY * 7);

            String refreshToken = JwtUtil.generateToken(
                    account.getUsername(),
                    null,
                    request.getRequestURL().toString(),
                    JwtUtil.ONE_DAY * 14);
            CredentialDTO credential = new CredentialDTO(accessToken, refreshToken,roles);
            return ResponseEntity.ok(credential);
        } catch (Exception ex) {
            //show error
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
