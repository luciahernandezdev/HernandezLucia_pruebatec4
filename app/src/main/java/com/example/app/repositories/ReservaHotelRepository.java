package com.example.app.repositories;


import com.example.app.entities.ReservaHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

public interface ReservaHotelRepository extends JpaRepository<ReservaHotel, Long> {
    boolean existsByHotelIdAndDateFromAndDateToAndRoomType(Long hotelId, LocalDate dateFrom, LocalDate dateTo, String roomType);
}

