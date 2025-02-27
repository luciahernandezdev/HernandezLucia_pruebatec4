package com.example.app.services;

import com.example.app.dtos.ReservaVueloDTO;
import com.example.app.entities.ReservaVuelo;
import com.example.app.entities.Vuelo;
import com.example.app.exceptions.ErrorResponse;
import com.example.app.repositories.ReservaVueloRepository;
import com.example.app.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaVueloService {

    @Autowired
    private ReservaVueloRepository reservaVueloRepository;

    @Autowired
    private VueloRepository vueloRepository;


    public ResponseEntity<?> crearReserva(ReservaVueloDTO dto) {

        if (dto.getFlightNumber() == null || dto.getFlightNumber().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Número de vuelo inválido", "El número de vuelo es obligatorio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Vuelo vuelo = vueloRepository.findByFlightNumber(dto.getFlightNumber())
                .orElse(null);

        if (vuelo == null) {
            ErrorResponse errorResponse = new ErrorResponse("Vuelo no encontrado", "No se encontró un vuelo con ese número.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ReservaVuelo reserva = dtoToEntity(dto, vuelo.getId());

        double montoTotal = calcularMontoTotal(reserva);
        reserva.setMontoTotal(montoTotal);

        reserva = reservaVueloRepository.save(reserva);

        return new ResponseEntity<>(entityToDto(reserva, vuelo, montoTotal), HttpStatus.CREATED);
    }


    public ResponseEntity<?> obtenerReservasPorFechaYDestino(LocalDate date, String destination) {
        if (date == null || destination == null || destination.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Parámetros inválidos", "La fecha y el destino son obligatorios.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        List<ReservaVuelo> reservas = reservaVueloRepository.findByDateAndDestination(date, destination);

        if (reservas.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("No se encontraron reservas", "No hay reservas disponibles para la fecha y destino solicitados.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reservas.stream().map(reserva -> {
            Vuelo vuelo = vueloRepository.findById(reserva.getVueloId()).orElse(null);
            double montoTotal = calcularMontoTotal(reserva);
            return entityToDto(reserva, vuelo, montoTotal);
        }).collect(Collectors.toList()), HttpStatus.OK);
    }

    private ReservaVuelo dtoToEntity(ReservaVueloDTO dto, Long vueloId) {
        ReservaVuelo reserva = new ReservaVuelo();
        reserva.setVueloId(vueloId);
        reserva.setDate(dto.getDate());
        reserva.setOrigin(dto.getOrigin());
        reserva.setDestination(dto.getDestination());
        reserva.setPeopleQ(dto.getPeopleQ());
        reserva.setSeatType(dto.getSeatType());
        return reserva;
    }

    // Método para calcular el monto total
    private double calcularMontoTotal(ReservaVuelo reserva) {
        double precioPorPersona = 200;
        return reserva.getPeopleQ() * precioPorPersona;
    }

    private ReservaVueloDTO entityToDto(ReservaVuelo reserva, Vuelo vuelo, double montoTotal) {
        ReservaVueloDTO dto = new ReservaVueloDTO();
        dto.setId(reserva.getId());
        dto.setFlightNumber(vuelo != null ? vuelo.getFlightNumber() : "No disponible"); // Si no encontramos vuelo, le asignamos un valor por defecto
        dto.setDate(reserva.getDate());
        dto.setOrigin(reserva.getOrigin());
        dto.setDestination(reserva.getDestination());
        dto.setPeopleQ(reserva.getPeopleQ());
        dto.setSeatType(reserva.getSeatType());
        dto.setMontoTotal(montoTotal);
        return dto;
    }
}
