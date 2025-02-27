package com.example.app.services;

import com.example.app.dtos.VueloDTO;
import com.example.app.entities.Vuelo;
import com.example.app.exceptions.ErrorResponse;
import com.example.app.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VueloService {

    @Autowired
    private VueloRepository vueloRepository;

    // Obtener todos los vuelos
    public ResponseEntity<?> obtenerTodosLosVuelos() {
        List<Vuelo> vuelos = vueloRepository.findAll();
        if (vuelos.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("No se encontraron vuelos disponibles", "Error");
            return ResponseEntity.status(404).body(errorResponse);
        }
        List<VueloDTO> vuelosDTO = vuelos.stream()
                .map(this::convertirAVueloDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vuelosDTO);
    }

    // Obtener un vuelo por su ID
    public ResponseEntity<?> obtenerVueloPorId(Long id) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElse(null);
        if (vuelo == null) {
            ErrorResponse errorResponse = new ErrorResponse("Vuelo con ID " + id + " no encontrado.", "Error");
            return ResponseEntity.status(404).body(errorResponse);
        }
        VueloDTO vueloDTO = convertirAVueloDTO(vuelo);
        return ResponseEntity.ok(vueloDTO);
    }

    // Crear un nuevo vuelo
    public Vuelo crearVuelo(VueloDTO vueloDTO) {
        // Validar que los campos obligatorios no sean nulos o vacíos
        if (vueloDTO.getFlightNumber() == null || vueloDTO.getFlightNumber().trim().isEmpty()) {
            throw new RuntimeException("El número de vuelo es obligatorio.");
        }
        if (vueloDTO.getOrigin() == null || vueloDTO.getOrigin().trim().isEmpty()) {
            throw new RuntimeException("El origen es obligatorio.");
        }
        if (vueloDTO.getDestination() == null || vueloDTO.getDestination().trim().isEmpty()) {
            throw new RuntimeException("El destino es obligatorio.");
        }
        if (vueloDTO.getDate() == null) {
            throw new RuntimeException("La fecha del vuelo es obligatoria.");
        }

        // Verificar si ya existe un vuelo con ese número
        Optional<Vuelo> vueloExistente = vueloRepository.findByFlightNumber(vueloDTO.getFlightNumber());
        if (vueloExistente.isPresent()) {
            throw new RuntimeException("El vuelo con número " + vueloDTO.getFlightNumber() + " ya existe.");
        }

        // Crear y guardar el nuevo vuelo
        Vuelo vuelo = new Vuelo();
        vuelo.setFlightNumber(vueloDTO.getFlightNumber());
        vuelo.setName(vueloDTO.getName());
        vuelo.setOrigin(vueloDTO.getOrigin());
        vuelo.setDestination(vueloDTO.getDestination());
        vuelo.setSeatType(vueloDTO.getSeatType());
        vuelo.setFlightPrice(vueloDTO.getFlightPrice());
        vuelo.setDate(vueloDTO.getDate());

        return vueloRepository.save(vuelo);
    }

    // Actualizar un vuelo existente
    public ResponseEntity<?> actualizarVuelo(Long id, VueloDTO vueloDTO) {
        Optional<Vuelo> vueloOptional = vueloRepository.findById(id);
        if (vueloOptional.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Vuelo con ID " + id + " no encontrado para actualización.", "Error");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Vuelo vuelo = vueloOptional.get();
        vuelo.setFlightNumber(vueloDTO.getFlightNumber());
        vuelo.setName(vueloDTO.getName());
        vuelo.setOrigin(vueloDTO.getOrigin());
        vuelo.setDestination(vueloDTO.getDestination());
        vuelo.setSeatType(vueloDTO.getSeatType());
        vuelo.setFlightPrice(vueloDTO.getFlightPrice());
        vuelo.setDate(vueloDTO.getDate());

        Vuelo vueloActualizado = vueloRepository.save(vuelo);
        return ResponseEntity.ok(convertirAVueloDTO(vueloActualizado));
    }

    // Eliminar un vuelo
    public ResponseEntity<?> eliminarVuelo(Long id) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElse(null);
        if (vuelo == null) {
            ErrorResponse errorResponse = new ErrorResponse("Vuelo con ID " + id + " no encontrado para eliminación.", "Error");
            return ResponseEntity.status(404).body(errorResponse);
        }
        vueloRepository.delete(vuelo);
        return ResponseEntity.status(204).build();  // 204 No Content: éxito sin contenido
    }

    // Convertir de entidad Vuelo a VueloDTO
    private VueloDTO convertirAVueloDTO(Vuelo vuelo) {
        VueloDTO vueloDTO = new VueloDTO();
        vueloDTO.setId(vuelo.getId());
        vueloDTO.setFlightNumber(vuelo.getFlightNumber());
        vueloDTO.setName(vuelo.getName());
        vueloDTO.setOrigin(vuelo.getOrigin());
        vueloDTO.setDestination(vuelo.getDestination());
        vueloDTO.setSeatType(vuelo.getSeatType());
        vueloDTO.setFlightPrice(vuelo.getFlightPrice());
        vueloDTO.setDate(vuelo.getDate());
        return vueloDTO;
    }

    // Convertir de VueloDTO a entidad Vuelo
    private Vuelo convertirAEntidad(VueloDTO vueloDTO) {
        Vuelo vuelo = new Vuelo();
        vuelo.setFlightNumber(vueloDTO.getFlightNumber());
        vuelo.setName(vueloDTO.getName());
        vuelo.setOrigin(vueloDTO.getOrigin());
        vuelo.setDestination(vueloDTO.getDestination());
        vuelo.setSeatType(vueloDTO.getSeatType());
        vuelo.setFlightPrice(vueloDTO.getFlightPrice());
        vuelo.setDate(vueloDTO.getDate());
        return vuelo;
    }
}
