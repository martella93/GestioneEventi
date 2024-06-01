package it.epicode.GestioneEventi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EventDto {

    private int id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Date is mandatory")
    private LocalDate date;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotNull(message = "Available seats is mandatory")
    private int availableSeats;

    private List<Integer> attendeeIds;
}
