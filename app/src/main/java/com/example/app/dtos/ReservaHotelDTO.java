package com.example.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaHotelDTO {

    private Long hotelId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int peopleQ;
    private String roomType;
    private boolean confirmada;
    private List<HabitacionDTO> habitaciones;
    private double montoTotal;
}
