package it.epicode.GestioneEventi.repository;

import it.epicode.GestioneEventi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e JOIN e.attendees a WHERE a.id = :userId")
    List<Event> findByAttendeesId(int userId);
}
