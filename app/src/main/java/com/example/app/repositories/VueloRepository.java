package com.example.app.repositories;

import com.example.app.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    Optional<Vuelo> findByFlightNumber(String flightNumber);
}
