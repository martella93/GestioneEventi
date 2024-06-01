package it.epicode.GestioneEventi.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private int availableSeats;

    @ManyToMany(mappedBy = "bookedEvents")
    private List<User> attendees = new ArrayList<>();

}
