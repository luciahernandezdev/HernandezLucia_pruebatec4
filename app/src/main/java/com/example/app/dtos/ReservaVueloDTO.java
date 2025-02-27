package com.example.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaVueloDTO {

    private Long id;
    private String flightNumber;
    private LocalDate date;
    private String origin;
    private String destination;
    private int peopleQ;
    private String seatType;
    private double montoTotal;
}

