package com.example.app.dtos;

import lombok.*;

import java.time.LocalDate;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VueloDTO {

    private Long id;
    private String flightNumber;
    private String name;
    private String origin;
    private String destination;
    private String seatType;
    private Double flightPrice;
    private LocalDate date;
}
