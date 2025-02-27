package com.example.app.repositories;

import com.example.app.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    // Buscar habitaciones disponibles dentro de un rango de fechas y en una ciudad específica
    List<Habitacion> findByFechaEntradaBeforeAndFechaSalidaAfterAndHotel_Ciudad(LocalDate fechaEntrada, LocalDate fechaSalida, String ciudad);
    List<Habitacion> findAllByIdIn(List<Long> ids);
// Método para encontrar habitación por ID
}
