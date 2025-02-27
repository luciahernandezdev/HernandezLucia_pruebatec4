package com.example.app.controllers;

import com.example.app.dtos.VueloDTO;
import com.example.app.entities.Vuelo;
import com.example.app.services.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agency/flights")
public class VueloController {

    @Autowired
    private VueloService vueloService;

    // Obtener todos los vuelos
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosVuelos() {
        return vueloService.obtenerTodosLosVuelos();
    }

    // Obtener un vuelo específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVueloPorId(@PathVariable Long id) {
        return vueloService.obtenerVueloPorId(id);
    }

    // Crear un nuevo vuelo
    @PostMapping("/new")
    public ResponseEntity<?> crearVuelo(@RequestBody VueloDTO vueloDTO) {
        try {
            Vuelo vuelo = vueloService.crearVuelo(vueloDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(vuelo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar un vuelo existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> actualizarVuelo(@PathVariable Long id, @RequestBody VueloDTO vueloDTO) {
        return vueloService.actualizarVuelo(id, vueloDTO);
    }

    // Eliminar un vuelo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarVuelo(@PathVariable Long id) {
        return vueloService.eliminarVuelo(id);
    }
}
