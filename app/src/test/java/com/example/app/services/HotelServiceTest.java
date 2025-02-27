package com.example.app.services;

import com.example.app.dtos.HotelDTO;
import com.example.app.entities.Habitacion;
import com.example.app.entities.Hotel;
import com.example.app.entities.Usuario;
import com.example.app.repositories.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @InjectMocks
    private HotelService service;

    @Mock
    private HotelRepository repository;

    @Test
    @DisplayName("Mostrar Hoteles")
    void mostrarHoteles() {
        // Crear instancias de hotel con tus datos
        Hotel hotel1 = new Hotel(1L, "ABC-123", "Las Palmas", "Canarias", false, null);
        Habitacion habitacion1 = new Habitacion(1L, "DOBLE", LocalDate.now(),
                LocalDate.of(2025, 2, 1), 2, true, hotel1, null);
        hotel1.setHabitaciones(List.of(habitacion1));

        Hotel hotel2 = new Hotel(2L, "ADF-14522", "SENATOR", "Madrid", false, List.of());

        List<Hotel> hoteles = List.of(hotel1, hotel2);

        when(repository.findAll()).thenReturn(hoteles);

        List<HotelDTO> hotelesDTO = service.obtenerTodosLosHoteles();

        // Verificar resultados
        assertThat(hotelesDTO.get(0).getId()).isEqualTo(1L);
        assertThat(hotelesDTO.get(0).getHotelCode()).isEqualTo("ABC-123");
        assertThat(hotelesDTO.get(0).getName()).isEqualTo("Las Palmas");
        assertThat(hotelesDTO.get(0).getCiudad()).isEqualTo("Canarias");


        assertThat(hotelesDTO.get(0).getHabitaciones()).isNotNull();
        assertThat(hotelesDTO.get(0).getHabitaciones().size()).isEqualTo(1);

        assertThat(hotelesDTO.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("No hay hoteles registrados")
    void noHayHoteles() {
        when(repository.findAll()).thenReturn(List.of());

        List<HotelDTO> hotelesDTO = service.obtenerTodosLosHoteles();

        assertThat(hotelesDTO.size()).isEqualTo(0);
    }
}
