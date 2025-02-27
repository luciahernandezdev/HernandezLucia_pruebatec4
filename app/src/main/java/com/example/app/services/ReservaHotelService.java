package com.example.app.services;

import com.example.app.dtos.HabitacionDTO;
import com.example.app.dtos.ReservaHotelDTO;
import com.example.app.entities.Habitacion;
import com.example.app.entities.Hotel;
import com.example.app.entities.ReservaHotel;
import com.example.app.repositories.HabitacionRepository;
import com.example.app.repositories.HotelRepository;
import com.example.app.repositories.ReservaHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaHotelService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ReservaHotelRepository reservaHotelRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<HabitacionDTO> obtenerHabitacionesDisponibles(LocalDate dateFrom, LocalDate dateTo, String destination) {

        List<Habitacion> habitacionesDisponibles = habitacionRepository.findByFechaEntradaBeforeAndFechaSalidaAfterAndHotel_Ciudad(
                dateFrom, dateTo, destination);

        return habitacionesDisponibles.stream()
                .map(this::convertirAHabitacionDTO)
                .collect(Collectors.toList());
    }

    public ReservaHotelDTO crearReserva(ReservaHotelDTO reservaHotelDTO) {
        // Buscar el hotel por ID
        Hotel hotel = hotelRepository.findById(reservaHotelDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        // Convertir HabitacionDTO a entidades Habitacion
        List<Habitacion> habitaciones = convertirHabitacionDTOaEntidad(reservaHotelDTO.getHabitaciones());

        // Crear la reserva de hotel
        ReservaHotel reservaHotel = new ReservaHotel();
        reservaHotel.setHotel(hotel);
        reservaHotel.setDateFrom(reservaHotelDTO.getDateFrom());
        reservaHotel.setDateTo(reservaHotelDTO.getDateTo());
        reservaHotel.setPeopleQ(reservaHotelDTO.getPeopleQ());
        reservaHotel.setRoomType(reservaHotelDTO.getRoomType());
        reservaHotel.setConfirmada(reservaHotelDTO.isConfirmada());
        reservaHotel.setHabitaciones(habitaciones);

        // Guardar la reserva en la base de datos
        reservaHotel = reservaHotelRepository.save(reservaHotel);

        // Devolver el DTO de la reserva creada
        return convertirEntidadADTO(reservaHotel);
    }

    // Método para convertir HabitacionDTO a Habitacion (entidad)
    private List<Habitacion> convertirHabitacionDTOaEntidad(List<HabitacionDTO> habitacionesDTO) {
        return habitacionesDTO.stream()
                .map(dto -> habitacionRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Habitacion no encontrada")))
                .collect(Collectors.toList());
    }

    // Método para convertir la entidad ReservaHotel a ReservaHotelDTO
    private ReservaHotelDTO convertirEntidadADTO(ReservaHotel reservaHotel) {
        List<HabitacionDTO> habitacionesDTO = reservaHotel.getHabitaciones().stream()
                .map(this::convertirAHabitacionDTO)
                .collect(Collectors.toList());

        return new ReservaHotelDTO(
                reservaHotel.getHotel().getId(),
                reservaHotel.getDateFrom(),
                reservaHotel.getDateTo(),
                reservaHotel.getPeopleQ(),
                reservaHotel.getRoomType(),
                reservaHotel.isConfirmada(),
                habitacionesDTO
        );
    }

    // Método para convertir Habitacion a HabitacionDTO
    private HabitacionDTO convertirAHabitacionDTO(Habitacion habitacion) {
        return new HabitacionDTO(
                habitacion.getId(),
                habitacion.getTipoHabitacion(),
                habitacion.getFechaEntrada(),
                habitacion.getFechaSalida(),
                habitacion.getPeopleQ(),
                habitacion.getReservado(),
                habitacion.getHotel().getId()
        );
    }
}

