package it.epicode.GestioneEventi.controller;

import it.epicode.GestioneEventi.dto.UserDto;
import it.epicode.GestioneEventi.dto.UserLoginDto;
import it.epicode.GestioneEventi.exception.BadRequestException;
import it.epicode.GestioneEventi.service.AuthService;
import it.epicode.GestioneEventi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public String register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().
                    stream().
                    map(ObjectError::getDefaultMessage).
                    reduce("", ((s, s2) -> s+s2)));
        }
        return userService.saveUser(userDto);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated UserLoginDto userLoginDto,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().
                    stream().
                    map(ObjectError::getDefaultMessage).
                    reduce("", ((s, s2) -> s+s2)));
        }
        return authService.authenticateUserAndCreateToken(userLoginDto);
    }
}
