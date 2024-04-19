package com.user.user.adapters.driven.jpa.mysql.adapter;

import com.user.user.adapters.driven.jpa.mysql.entity.CustomUserDetails;
import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));

        List<RoleEntity> roles = userEntity.getRoles();
        return CustomUserDetails.build(userEntity, roles);
    }
}
