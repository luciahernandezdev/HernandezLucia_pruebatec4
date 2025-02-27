package com.example.app.controllers;

import com.example.app.dtos.ReservaVueloDTO;
import com.example.app.services.ReservaVueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/agency")
public class ReservaVueloController {

    @Autowired
    private ReservaVueloService reservaVueloService;

    // Endpoint para crear una nueva reserva de vuelo
    @PostMapping("/flight-booking/new")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaVueloDTO dto) {
        return reservaVueloService.crearReserva(dto);
    }

    // /agency/flights?dateFrom=20/02/2025&dateTo=20/03/2025&origin="madrid"&destination="barcelona"
    // Endpoint para obtener reservas por fecha y destino
    @GetMapping("")
    public ResponseEntity<?> obtenerReservas(@RequestParam LocalDate date, @RequestParam String destination) {
        return reservaVueloService.obtenerReservasPorFechaYDestino(date, destination);
    }
}
