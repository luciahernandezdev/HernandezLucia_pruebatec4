package com.example.app.repositories;

import com.example.app.entities.ReservaVuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaVueloRepository extends JpaRepository<ReservaVuelo, Long> {
    List<ReservaVuelo> findByDateAndDestination(LocalDate date, String destination);
    Long countByVueloIdAndEstado(Long vueloId, String estado);


}
