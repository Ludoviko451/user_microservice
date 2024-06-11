package com.user.user.adapters.driven.jpa.mysql.adapter;

import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.user.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    public UserAdapter(IUserRepository userRepository, IUserEntityMapper userEntityMapper, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveUser(User user, Long idRole) {
        UserEntity userEntity = userEntityMapper.toEntity(user);

        UserEntity savedEntity = addRolesToUser(userEntity, idRole);

         userRepository.save(savedEntity);
    }

    private UserEntity addRolesToUser(UserEntity userEntity, Long idRole) {
        RoleEntity roleEntity = roleRepository.findById(idRole).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        userEntity.setRoles(Collections.singletonList(roleEntity));

        return userEntity;
    }


    @Override
    public Optional<User> findByEmail(String email) {


        return userRepository.findByEmail(email).map(userEntityMapper::toModel);
    }

    @Override
    public Optional<User> findByDni(String dni) {
        return userRepository.findByDni(dni).map(userEntityMapper::toModel);
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matchesPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }


}
