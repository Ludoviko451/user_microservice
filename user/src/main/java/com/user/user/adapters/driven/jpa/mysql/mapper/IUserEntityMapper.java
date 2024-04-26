package com.user.user.adapters.driven.jpa.mysql.mapper;
import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;


@Mapper(componentModel = "spring")
public interface IUserEntityMapper {

    @Mapping(target = "role", source = "roles")
    User toModel(UserEntity userEntity);



    @Mapping(target = "roles", source = "role")
    UserEntity toEntity(User user);


    List<User> toModel(List<UserEntity> userEntities);
}
