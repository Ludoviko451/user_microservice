package com.user.user.adapters.driving.http.mapper;

import com.user.user.adapters.driving.http.dto.response.UserResponse;
import com.user.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserResponseMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    UserResponse toResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);
}
