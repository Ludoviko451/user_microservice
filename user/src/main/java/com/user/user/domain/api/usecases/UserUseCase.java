package com.user.user.domain.api.usecases;

import com.user.user.adapters.driven.jpa.mysql.exception.PhoneNumberNotValidException;
import com.user.user.adapters.driven.jpa.mysql.exception.UserAlreadyExists;
import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.configuration.Constants;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public void saveUser(User user, Long roleId) {

        validateData(user);

        userPersistencePort.saveUser(user, roleId);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }
    @Override
    public User findUserByEmail(String email) {
        return userPersistencePort.findByEmail(email).orElseThrow(() -> new UserNotExistException(email));
    }

    private void validateData(User user){
        if (userPersistencePort.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExists(user.getEmail());
        }
        if (userPersistencePort.findByDni(user.getDni()).isPresent()) {
            throw new UserAlreadyExists(user.getDni());
        }
        // Expresión regular para validar el número de teléfono
        Pattern pattern = Pattern.compile(Constants.PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(user.getPhoneNumber());


        // Validar el número de teléfono
        if (!matcher.matches()) {
            throw new PhoneNumberNotValidException();
        }
    }
}
