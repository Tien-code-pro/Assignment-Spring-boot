package com.example.orderdemo.service;

import com.example.orderdemo.dto.AccountDTO;
import com.example.orderdemo.dto.RegisterDTO;
import com.example.orderdemo.entity.Account;
import com.example.orderdemo.entity.Role;
import com.example.orderdemo.repository.AccountRepository;
import com.example.orderdemo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private static final String USER_ROLE = "user";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        Account account = accountOptional.orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role:
        account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetail
                = new User(account.getUsername(), account.getPassword(), authorities);
        return
                userDetail;

    }

    public AccountDTO saveAccount(RegisterDTO registerDTO) {
        //create new user role if not exist
        Set<Role> roles = new HashSet<>();
        for (Role role: registerDTO.getRoles()) {
            Optional<Role> userRoleOptional = roleRepository.findByName(role.getName());
            Role userRole = userRoleOptional.orElse(null);
            if (userRole == null) {
                return null;
            }
            roles.add(userRoleOptional.get());
        }

        //check if username has exist
        Optional<Account> byUsername = accountRepository.findByUsername(registerDTO.getUsername());
        if (byUsername.isPresent()) {
            return null;
        }

        Account account = new Account();

        account.setUsername(registerDTO.getUsername());
        account.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setStatus(1);
        account.setRoles(roles);
        Account save = accountRepository.save(account);
        return new AccountDTO(save);
    }

    public Account getAccount(String username) {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        return byUsername.orElse(null);
    }
}
