package it.epicode.GestioneEventi.service;

import it.epicode.GestioneEventi.dto.UserDto;
import it.epicode.GestioneEventi.dto.UserLoginDto;
import it.epicode.GestioneEventi.entity.User;
import it.epicode.GestioneEventi.exception.UnauthorizedException;
import it.epicode.GestioneEventi.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndCreateToken(UserLoginDto userLoginDto){
        User user = userService.getUserByUsername(userLoginDto.getUsername());

        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            return jwtTool.createToken(user);
        }
        else {
            throw new UnauthorizedException("Error in authprization, relogin");
        }
    }
}
