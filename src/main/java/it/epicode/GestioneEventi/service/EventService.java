package it.epicode.GestioneEventi.service;

import it.epicode.GestioneEventi.dto.EventDto;
import it.epicode.GestioneEventi.entity.Event;
import it.epicode.GestioneEventi.entity.User;
import it.epicode.GestioneEventi.exception.EventNotFoundException;
import it.epicode.GestioneEventi.exception.UserNotFoundException;
import it.epicode.GestioneEventi.repository.EventRepository;
import it.epicode.GestioneEventi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;


    public String saveEvent(EventDto eventDto){

        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        event.setAvailableSeats(eventDto.getAvailableSeats());

        eventRepository.save(event);
        return "Event with id= " + event.getId() + " saved";
    }

    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

    public Optional<Event> getEventsById(int id){
        return eventRepository.findById(id);
    }

    public Event updateEvent(int id, EventDto eventDto){
        Optional<Event> eventOptional = getEventsById(id);

        if (eventOptional.isPresent()){
            Event event = eventOptional.get();
            event.setTitle(eventDto.getTitle());
            event.setDescription(eventDto.getDescription());
            event.setDate(eventDto.getDate());
            event.setLocation(eventDto.getLocation());
            event.setAvailableSeats(eventDto.getAvailableSeats());

            return eventRepository.save(event);
        }
        else {
            throw new EventNotFoundException("Event with id= " + id + " not found");
        }
    }

    public String deleteEvent(int id){
        Optional<Event> eventOptional = getEventsById(id);

        if (eventOptional.isPresent()){
            eventRepository.delete(eventOptional.get());
            return "Event with id " + id + "  deleted";
        }
        else {
            throw new EventNotFoundException("Event with id= " + id + " not found");
        }
    }

    public String bookEvent(int eventId, int numberOfSeats) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException("Event with id= " + eventId + " not found");
        }

        Event event = eventOptional.get();
        int availableSeats = event.getAvailableSeats();

        if (availableSeats >= numberOfSeats) {
            event.setAvailableSeats(availableSeats - numberOfSeats);
            event.getAttendees().add(user);
            user.getBookedEvents().add(event);
            eventRepository.save(event);
            userRepository.save(user);
            return numberOfSeats + " seats booked for event with id= " + eventId;
        } else {
            throw new IllegalArgumentException("Not enough seats available for booking");
        }
    }

    public List<Event> getBookedEventsForUser(int userId) {
        return eventRepository.findByAttendeesId(userId);
    }

    public String cancelBooking(int id, int userId) {
        Optional<Event> eventOptional = eventRepository.findById(id);

        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException("Event with id= " + id + " not found");
        }

        Event event = eventOptional.get();

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with id= " + userId + " not found");
        }

        User user = userOptional.get();

        List<Event> userBookedEvents = user.getBookedEvents().stream()
                .filter(e -> e.getId() == id)
                .collect(Collectors.toList());

        event.getAttendees().remove(user);
        user.getBookedEvents().removeAll(userBookedEvents);
        event.setAvailableSeats(event.getAvailableSeats() + userBookedEvents.size());

        eventRepository.save(event);
        userRepository.save(user);

        return "Booking canceled for user with id= " + userId + " for event with id= " + id;

    }
}
