package com.user.user.adapters.driven.jpa.mysql.adapter;

import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.adapters.driven.jpa.mysql.exception.PasswordMismatchException;
import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.user.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.user.user.domain.model.Role;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    public UserAdapter(IUserRepository userRepository, IUserEntityMapper userEntityMapper, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    //METODO PARA CREAR UN NUEVO TUTOR
    @Override
    public void saveUser(User user, Long idRole) {

        List<UserEntity> userEntities = userRepository.findAll();
        UserEntity userEntity = userEntityMapper.toEntity(user);

        for (UserEntity existingUser : userEntities) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                throw new RuntimeException("User already exists");
            }
        }
         if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        UserEntity savedEntity = addRolesToUser(userEntity, idRole);

         userRepository.save(savedEntity);
    }

    private UserEntity addRolesToUser(UserEntity userEntity, Long idRole) {
        RoleEntity roleEntity = roleRepository.findById(idRole).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        userEntity.setRoles(Collections.singletonList(roleEntity));

        return userEntity;
    }





    @Override
    public List<User> getAllUsers() {
        return userEntityMapper.toModel(userRepository.findAll());
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
    public Authentication login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistException(email));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordMismatchException();
        }

        return new UsernamePasswordAuthenticationToken(email, password);
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}