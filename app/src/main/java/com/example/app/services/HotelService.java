package com.example.app.services;

import com.example.app.dtos.HabitacionDTO;
import com.example.app.dtos.HotelDTO;
import com.example.app.entities.Habitacion;
import com.example.app.entities.Hotel;
import com.example.app.exceptions.ErrorResponse;
import com.example.app.repositories.HabitacionRepository;
import com.example.app.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    // Obtener todos los hoteles
    public List<HotelDTO> obtenerTodosLosHoteles() {

        List<Hotel> hoteles = hotelRepository.findAll();

        return hoteles.stream()
                .map(this::convertirAHotelDTO)
                .collect(Collectors.toList());
    }

    // Obtener un hotel por su ID
    public Object obtenerHotelPorId(Long id) {
        // Buscar el hotel en la base de datos
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        // Si el hotel existe, lo convertimos en DTO y lo retornamos
        if (hotelOptional.isPresent()) {
            return convertirAHotelDTO(hotelOptional.get());
        }

        // Si no se encuentra el hotel, creamos un ErrorResponse y lo devolvemos
        ErrorResponse errorResponse = new ErrorResponse("Hotel con ID " + id + " no encontrado.", "Error");
        return errorResponse;
    }

    // Crear un nuevo hotel
    // Método para crear un hotel junto con sus habitaciones
    public Hotel crearHotel(HotelDTO hotelDTO) {
        // Crear la entidad Hotel a partir del DTO
        Hotel hotel = new Hotel();
        hotel.setHotelCode(hotelDTO.getHotelCode());
        hotel.setName(hotelDTO.getName());
        hotel.setCiudad(hotelDTO.getCiudad());

        // Guardar el hotel primero para obtener su id (si es necesario)
        Hotel hotelGuardado = hotelRepository.save(hotel);

        // Si hay habitaciones asociadas al hotel, crearlas
        if (hotelDTO.getHabitaciones() != null && !hotelDTO.getHabitaciones().isEmpty()) {
            for (HabitacionDTO habitacionDTO : hotelDTO.getHabitaciones()) {
                Habitacion habitacion = new Habitacion();
                habitacion.setTipoHabitacion(habitacionDTO.getTipoHabitacion());
                habitacion.setFechaEntrada(habitacionDTO.getFechaEntrada());
                habitacion.setFechaSalida(habitacionDTO.getFechaSalida());
                habitacion.setReservado(habitacionDTO.getReservado());

                // Asociar la habitación con el hotel
                habitacion.setHotel(hotelGuardado);

                // Guardar la habitación en la base de datos
                habitacionRepository.save(habitacion);
            }
        }

        // Devolver el hotel con sus habitaciones ya guardadas
        return hotelGuardado;
    }

    // Actualizar un hotel existente
    public HotelDTO actualizarHotel(Long id, HotelDTO hotelDTO) {
        // Buscar el hotel existente por su ID
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        // Verificar si el hotel existe
        if (hotelOptional.isPresent()) {
            // Si el hotel existe, obtenemos el objeto Hotel
            Hotel hotel = hotelOptional.get();

            // Actualizamos los campos del hotel con los valores del DTO
            hotel.setHotelCode(hotelDTO.getHotelCode());
            hotel.setName(hotelDTO.getName());
            hotel.setCiudad(hotelDTO.getCiudad());

            // Guardar el hotel actualizado en la base de datos
            Hotel hotelActualizado = hotelRepository.save(hotel);

            // Convertir la entidad Hotel actualizada a un DTO y devolverlo
            return convertirAHotelDTO(hotelActualizado);
        }

        return null;
    }

    // Eliminar un hotel
    public ResponseEntity<?> eliminarHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElse(null);
        if (hotel == null) {
            ErrorResponse errorResponse = new ErrorResponse("Hotel con ID " + id + " no encontrado para eliminación.", "Error");
            return ResponseEntity.status(404).body(errorResponse);
        }
        hotelRepository.delete(hotel);
        return ResponseEntity.status(204).build();
    }

    // Convertir de entidad Hotel a HotelDTO
    private HotelDTO convertirAHotelDTO(Hotel hotel) {
        List<HabitacionDTO> habitacionesDto = hotel.getHabitaciones().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());

        return new HotelDTO(
                hotel.getId(),
                hotel.getHotelCode(),
                hotel.getName(),
                hotel.getCiudad(),
                habitacionesDto
        );
    }

    // Convertir de entidad Habitacion a HabitacionDTO
    private HabitacionDTO convertirADto(Habitacion habitacion) {
        HabitacionDTO habitacionDto = new HabitacionDTO();
        habitacionDto.setTipoHabitacion(habitacion.getTipoHabitacion());
        habitacionDto.setFechaEntrada(habitacion.getFechaEntrada());
        habitacionDto.setFechaSalida(habitacion.getFechaSalida());
        habitacionDto.setReservado(habitacion.getReservado());
        habitacionDto.setHotelId(habitacion.getHotel().getId());
        return habitacionDto;
    }

    // Convertir de HotelDTO a entidad Hotel
    private Hotel convertirAEntidad(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.getId());
        hotel.setHotelCode(hotelDTO.getHotelCode());
        hotel.setName(hotelDTO.getName());
        hotel.setCiudad(hotelDTO.getCiudad());

        // Convertir las habitaciones y asignarlas al hotel
        if (hotelDTO.getHabitaciones() != null) {
            List<Habitacion> habitaciones = hotelDTO.getHabitaciones().stream()
                    .map(this::convertirAEntidad)
                    .collect(Collectors.toList());
            hotel.setHabitaciones(habitaciones);
        }
        return hotel;
    }

    // Convertir de HabitacionDTO a entidad Habitacion
    private Habitacion convertirAEntidad(HabitacionDTO habitacionDto) {
        Habitacion habitacion = new Habitacion();
        habitacion.setTipoHabitacion(habitacionDto.getTipoHabitacion());
        habitacion.setFechaEntrada(habitacionDto.getFechaEntrada());
        habitacion.setFechaSalida(habitacionDto.getFechaSalida());
        habitacion.setReservado(habitacionDto.getReservado());

        return habitacion;
    }
}
