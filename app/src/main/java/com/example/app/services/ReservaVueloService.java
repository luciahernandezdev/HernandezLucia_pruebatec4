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

        // Guardar en BD
        reserva = reservaVueloRepository.save(reserva);

        // Convertir de Entidad a DTO y retornar
        return new ResponseEntity<>(entityToDto(reserva), HttpStatus.CREATED);
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
        return new ResponseEntity<>(reservas.stream().map(this::entityToDto).collect(Collectors.toList()), HttpStatus.OK);
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


    private ReservaVueloDTO entityToDto(ReservaVuelo reserva) {
        ReservaVueloDTO dto = new ReservaVueloDTO();
        dto.setFlightNumber(null); // Puedes obtenerlo desde vueloRepository si lo necesitas
        dto.setDate(reserva.getDate());
        dto.setOrigin(reserva.getOrigin());
        dto.setDestination(reserva.getDestination());
        dto.setPeopleQ(reserva.getPeopleQ());
        dto.setSeatType(reserva.getSeatType());
        return dto;
    }
}
