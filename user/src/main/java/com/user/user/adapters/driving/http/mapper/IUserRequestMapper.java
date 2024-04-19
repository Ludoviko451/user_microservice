package com.user.user.adapters.driving.http.mapper;

import com.user.user.adapters.driving.http.dto.request.UserRequest;
import com.user.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {

    @Mapping(target = "id", ignore = true)
    User addRequestToUser(UserRequest userRequest);
}
