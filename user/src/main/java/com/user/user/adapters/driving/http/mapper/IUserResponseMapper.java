package com.user.user.adapters.driving.http.mapper;
import com.user.user.adapters.driving.http.dto.response.UserResponse;
import com.user.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {


    UserResponse toResponse(User user);


    List<UserResponse> toUserResponseList(List<User> users);
}
