package it.epicode.GestioneEventi.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message){
        super(message);
    }
}
