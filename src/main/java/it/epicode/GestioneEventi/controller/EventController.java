package it.epicode.GestioneEventi.controller;

import it.epicode.GestioneEventi.dto.EventDto;
import it.epicode.GestioneEventi.entity.Event;
import it.epicode.GestioneEventi.exception.BadRequestException;
import it.epicode.GestioneEventi.exception.EventNotFoundException;
import it.epicode.GestioneEventi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/api/event")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public String saveEvent(@RequestBody @Validated EventDto eventDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().
                    stream().
                    map(ObjectError::getDefaultMessage).
                    reduce("", ((s, s2) -> s+s2)));
        }
        return eventService.saveEvent(eventDto);
    }

    @GetMapping("/api/event")
    public List<Event> getEvents(){
        return eventService.getEvents();
    }

    @GetMapping("/api/event/{id}")
    public Event getEventById(@PathVariable int id){
        Optional<Event> eventOptional = eventService.getEventsById(id);

        if (eventOptional.isPresent()){
            return eventOptional.get();
        }
        else {
            throw new EventNotFoundException("Event con id " + id + " not found");
        }
    }

    @PutMapping("/api/event/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event updateEvent(@PathVariable int id, @RequestBody @Validated EventDto eventDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().
                    stream().
                    map(ObjectError::getDefaultMessage).
                    reduce("", ((s, s2) -> s+s2)));
        }
        return eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping("/api/event/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public String deleteEvent(@PathVariable int id){
        return eventService.deleteEvent(id);
    }

    @PostMapping("/api/event/{id}/book")
    @PreAuthorize("hasAuthority('USER')")
    public String bookEvent(@PathVariable int id, @RequestParam int numberOfSeats) {
        return eventService.bookEvent(id, numberOfSeats);
    }



    @GetMapping("/api/event/{userId}/booked")
    @PreAuthorize("hasAuthority('USER')")
    public List<Event> getBookedEventsForUser(@PathVariable int userId) {
        return eventService.getBookedEventsForUser(userId);
    }

    @DeleteMapping("/api/event/{id}/delete")
    @PreAuthorize("hasAuthority('USER')")
    public String cancelBooking(@PathVariable int id, @RequestParam int userId) {
        return eventService.cancelBooking(id, userId);
    }

}
