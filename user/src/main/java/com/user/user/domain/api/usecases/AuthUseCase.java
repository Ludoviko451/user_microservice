package com.user.user.domain.api.usecases;
import com.user.user.adapters.driven.jpa.mysql.exception.PasswordMismatchException;
import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.configuration.Constants;
import com.user.user.domain.api.IAuthServicePort;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.configuration.security.jwt.JwtTokenProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;


public class AuthUseCase implements IAuthServicePort {
    private final IUserServicePort userServicePort;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserPersistencePort userPersistencePort;


    public AuthUseCase(IUserServicePort userServicePort, JwtTokenProvider jwtTokenProvider, IUserPersistencePort userPersistencePort) {
        this.userServicePort = userServicePort;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public String login(LoginDTO loginDTO) {

        User user = userServicePort.findUserByEmail(loginDTO.getEmail());

        if(!userPersistencePort.matchesPassword(loginDTO.getPassword(), user.getPassword())){
            throw new PasswordMismatchException(Constants.PASSWORD_MISMATCH_EXCEPTION);
        }

        List<SimpleGrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();


        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        return jwtTokenProvider.generateToken(userDetails);
    }
}
