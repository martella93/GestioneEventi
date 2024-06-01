package it.epicode.GestioneEventi.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class Error {
    private String message;
    private LocalDateTime dataError;
    private HttpStatus statoError;
}
