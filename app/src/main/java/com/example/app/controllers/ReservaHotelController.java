package com.example.app.controllers;

import com.example.app.dtos.HabitacionDTO;
import com.example.app.dtos.ReservaHotelDTO;
import com.example.app.entities.ReservaHotel;
import com.example.app.services.ReservaHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class ReservaHotelController {

    @Autowired
    private ReservaHotelService reservaHotelService;

    // Endpoint para obtener habitaciones disponibles según fechas y destino
    // /agency/rooms?dateFrom=01/02/2025&dateTo=01/04/2025&destination="madrid"
    @GetMapping("/rooms")
    public List<HabitacionDTO> obtenerHabitacionesDisponibles(
            @RequestParam("dateFrom") String dateFrom,
            @RequestParam("dateTo") String dateTo,
            @RequestParam("destination") String destination) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate fechaInicio = LocalDate.parse(dateFrom, formatter);
            LocalDate fechaFin = LocalDate.parse(dateTo, formatter);
            return reservaHotelService.obtenerHabitacionesDisponibles(fechaInicio, fechaFin, destination);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Usa dd/MM/yyyy (Ej: 01/02/2025)");
        }
    }

    // Endpoint para realizar una nueva reserva
    @PostMapping("/room-booking/new")
    public ResponseEntity<ReservaHotelDTO> crearReserva(@RequestBody ReservaHotelDTO reservaHotelDTO) {
        try {
            ReservaHotelDTO nuevaReserva = reservaHotelService.crearReserva(reservaHotelDTO);
            return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
