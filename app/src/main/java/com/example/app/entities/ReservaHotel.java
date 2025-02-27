package com.example.app.entities;

import com.example.app.dtos.HabitacionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserva_hotel")
public class ReservaHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int peopleQ;
    private String roomType;
    private boolean confirmada;

    @OneToMany(mappedBy = "reservaHotel", cascade = CascadeType.ALL)
    private List<Habitacion> habitaciones;
}
