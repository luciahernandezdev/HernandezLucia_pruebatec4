package com.example.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "habitacion")
public class Habitacion {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String tipoHabitacion;
        private LocalDate fechaEntrada;
        private LocalDate fechaSalida;
        private int peopleQ;
        private Boolean reservado;

        @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;

        @ManyToOne
        @JoinColumn(name = "reserva_id")
        private ReservaHotel reservaHotel;
}
