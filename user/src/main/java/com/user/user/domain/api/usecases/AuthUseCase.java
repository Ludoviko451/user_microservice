package com.user.user.domain.api.usecases;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.configuration.Constants;
import com.user.user.domain.api.IAuthServicePort;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.configuration.security.jwt.JwtTokenProvider;

public class AuthUseCase implements IAuthServicePort {
    private final IUserServicePort userServicePort;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserPersistencePort userPersistencePort;

    //NO USAR DEPENDENCIAS DE SPRING AQUI

    public AuthUseCase(IUserServicePort userServicePort, JwtTokenProvider jwtTokenProvider, IUserPersistencePort userPersistencePort) {
        this.userServicePort = userServicePort;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userPersistencePort = userPersistencePort;
    }

    @Override

    public String registerAdmin(User user) {

        return saveUser(user , Constants.ROLE_ADMIN);
    }



    @Override
    public String login(LoginDTO loginDTO) {


        return jwtTokenProvider.generateToken(userPersistencePort.login(loginDTO.getEmail(), loginDTO.getPassword()));
    }

    @Override
    public String registerTeacher(User user) {
        return saveUser(user, Constants.ROLE_TEACHER );
    }

    @Override
    public String registerStudent(User user) {
        return saveUser(user, Constants.ROLE_STUDENT );
    }


    private String saveUser(User user, Long roleid){
        String password = user.getPassword();
        //Usar metodo para encriptar contraseña
        user.setPassword(userPersistencePort.encryptPassword(password));
        userServicePort.saveUser(user, roleid);

        return Constants.USER_SAVED_MESSAGE;
    }
}
