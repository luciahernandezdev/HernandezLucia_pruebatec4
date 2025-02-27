package com.example.app.controllers;

import com.example.app.dtos.HotelDTO;
import com.example.app.entities.Hotel;
import com.example.app.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Obtener todos los hoteles
    @GetMapping
    public ResponseEntity<List<HotelDTO>> obtenerTodosLosHoteles() {
        List<HotelDTO> listaDeHoteles = hotelService.obtenerTodosLosHoteles();

        if (listaDeHoteles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaDeHoteles, HttpStatus.OK);
    }

    // Obtener un hotel específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerHotelPorId(@PathVariable Long id) {
        Object response = hotelService.obtenerHotelPorId(id);

        if (response instanceof ErrorResponse) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Crear un nuevo hotel
    @PostMapping("/new")
    public ResponseEntity<Hotel> crearHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotelCreado = hotelService.crearHotel(hotelDTO);
        return new ResponseEntity<>(hotelCreado, HttpStatus.CREATED);
    }

    // Actualizar un hotel existente por su ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<HotelDTO> actualizarHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        HotelDTO hotelActualizado = hotelService.actualizarHotel(id, hotelDTO);

        if (hotelActualizado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotelActualizado, HttpStatus.OK);
    }

    // Eliminar un hotel por su ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarHotel(@PathVariable Long id) {
        return hotelService.eliminarHotel(id);
    }
}
