package com.user.user.testData;

import com.user.user.domain.model.Role;
import com.user.user.domain.model.User;

import java.util.List;

public class DomainData {

    public static Role getRole(){
        return new Role(1L, testContants.ROLE_NAME);
    }

    public static User getUser(){
        return new User(1L, testContants.FIRST_NAME, testContants.LAST_NAME, testContants.DNI, testContants.PHONE_NUMBER, testContants.EMAIL, testContants.PASSWORD, List.of(getRole()));
    }
}
