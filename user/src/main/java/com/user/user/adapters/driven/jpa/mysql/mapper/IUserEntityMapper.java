package com.user.user.adapters.driven.jpa.mysql.mapper;

import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.domain.model.Role;
import com.user.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {


    User toModel(UserEntity userEntity);



    @Mapping(target = "roles", source = "role")
    UserEntity toEntity(User user);


    List<User> toModel(List<UserEntity> userEntities);
}
