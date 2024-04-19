package com.user.user.domain.api.usecases;


import com.user.user.adapters.driven.jpa.mysql.exception.PasswordMismatchException;
import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.configuration.Constants;
import com.user.user.domain.api.IAuthServicePort;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import com.user.user.security.jwt.JwtTokenProvider;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthUseCase implements IAuthServicePort {
    private final IUserServicePort userServicePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthUseCase(IUserServicePort userServicePort, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userServicePort = userServicePort;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override

    public String registerAdmin(User user) {

        return saveUser(user , Constants.ROLE_ADMIN);
    }



    @Override
    public String login(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        User user = userServicePort.findByEmail(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        if(!passwordEncoder.matches(password, user.getPassword())){

            throw new PasswordMismatchException();
        }

        return jwtTokenProvider.generateToken(authentication);
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServicePort.saveUser(user, roleid);

        return Constants.USER_SAVED_MESSAGE;
    }
}
