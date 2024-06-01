package it.epicode.GestioneEventi.dto;

import it.epicode.GestioneEventi.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class UserDto {

    private int id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private Role role;
    private List<Integer> organizedEventIds;
    private List<Integer> bookedEventIds;
}
