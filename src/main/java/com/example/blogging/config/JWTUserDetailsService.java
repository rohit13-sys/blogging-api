package com.example.blogging.config;

import com.example.blogging.entity.Role;
import com.example.blogging.entity.Users;
import com.example.blogging.exceptions.RoleNotFoundException;
import com.example.blogging.repository.UserReposiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserReposiory userReposiory;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user=userReposiory.findByEmail(userName).orElseThrow(()->new UsernameNotFoundException("UserName Not Found"));
       List<Role> roles=user.getRoles();
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName());
        }).collect(Collectors.toList());
        return new User(user.getEmail(),user.getPassword(),grantedAuthorities);

    }
}