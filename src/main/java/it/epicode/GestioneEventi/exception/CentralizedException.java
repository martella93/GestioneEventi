package it.epicode.GestioneEventi.exception;

import it.epicode.GestioneEventi.entity.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CentralizedException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> unauthorizedHandler(UnauthorizedException e){
        System.out.println("sono UnauthorizedException");
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setDataError(LocalDateTime.now());
        error.setStatoError(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestHandler(BadRequestException e){
        System.out.println("sono BadRequestException");
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setDataError(LocalDateTime.now());
        error.setStatoError(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleForbidden(AccessDeniedException ex){
        System.out.println("sono AccessDenied");
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setDataError(LocalDateTime.now());
        error.setStatoError(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleForbidden(Exception ex){
        System.out.println("sono Exception");
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setDataError(LocalDateTime.now());
        error.setStatoError(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
