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
        // Validar que el número de vuelo no sea nulo o vacío
        if (dto.getFlightNumber() == null || dto.getFlightNumber().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Número de vuelo inválido", "El número de vuelo es obligatorio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Buscar vuelo en la base de datos
        Vuelo vuelo = vueloRepository.findByFlightNumber(dto.getFlightNumber())
                .orElse(null);

        if (vuelo == null) {
            ErrorResponse errorResponse = new ErrorResponse("Vuelo no encontrado", "No se encontró un vuelo con ese número.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Convertir DTO a entidad
        ReservaVuelo reserva = dtoToEntity(dto, vuelo.getId());

        // Calcular el monto total
        double montoTotal = calcularMontoTotal(reserva);
        reserva.setMontoTotal(montoTotal);

        // Guardar en BD
        reserva = reservaVueloRepository.save(reserva);

        // Convertir de Entidad a DTO y retornar
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

        // Convertir las reservas encontradas a DTOs y devolver la lista
        return new ResponseEntity<>(reservas.stream().map(reserva -> {
            Vuelo vuelo = vueloRepository.findById(reserva.getVueloId()).orElse(null);
            double montoTotal = calcularMontoTotal(reserva); // Calcular monto por cada reserva
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
        double precioPorPersona = 200; // Asumimos que el precio por persona es 200 (puedes ajustarlo según el caso)
        return reserva.getPeopleQ() * precioPorPersona;
    }

    private ReservaVueloDTO entityToDto(ReservaVuelo reserva, Vuelo vuelo, double montoTotal) {
        ReservaVueloDTO dto = new ReservaVueloDTO();
        dto.setId(reserva.getId()); // Incluir el id de la reserva
        dto.setFlightNumber(vuelo != null ? vuelo.getFlightNumber() : "No disponible"); // Si no encontramos vuelo, le asignamos un valor por defecto
        dto.setDate(reserva.getDate());
        dto.setOrigin(reserva.getOrigin());
        dto.setDestination(reserva.getDestination());
        dto.setPeopleQ(reserva.getPeopleQ());
        dto.setSeatType(reserva.getSeatType());
        dto.setMontoTotal(montoTotal); // Incluir el monto total
        return dto;
    }
}
