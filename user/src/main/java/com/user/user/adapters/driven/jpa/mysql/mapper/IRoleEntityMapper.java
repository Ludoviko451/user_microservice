package com.user.user.adapters.driven.jpa.mysql.mapper;

import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.user.user.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleEntityMapper {

    Role toModel(RoleEntity roleEntity);
}
