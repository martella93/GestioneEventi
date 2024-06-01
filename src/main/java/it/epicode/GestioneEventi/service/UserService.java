package it.epicode.GestioneEventi.service;

import it.epicode.GestioneEventi.dto.UserDto;
import it.epicode.GestioneEventi.entity.Role;
import it.epicode.GestioneEventi.entity.User;
import it.epicode.GestioneEventi.exception.UserNotFoundException;
import it.epicode.GestioneEventi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        return "User con id " + user.getId() + " creato con successo";
    }

    public Page<User> getUser(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return userRepository.findAll(pageable);

    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    public User updateUser(int id, UserDto userDto){
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(userDto.getUsername());
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            return userRepository.save(user);
        }
        else {
            throw new UserNotFoundException("User with id= " + id + " not found");
        }
    }

    public String deleteUser(int id){
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()){
            userRepository.deleteById(id);
            return "User with id= " + id + " correctly deleted";
        }
        else {
            throw new UserNotFoundException("User with id= " + id + " not found");
        }
    }

    public User getUserByUsername(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with " + username + " not found");
        }
    }

}
