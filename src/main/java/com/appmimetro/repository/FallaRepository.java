package com.appmimetro.repository;

import com.appmimetro.model.Falla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FallaRepository extends JpaRepository<Falla, Long> {

    // Query existente para el panel de alertas agrupadas
    @Query("SELECT f.estacion, f.tipo, COUNT(f) as total FROM Falla f GROUP BY f.estacion, f.tipo ORDER BY total DESC")
    List<Object[]> contarFallasAgrupadas();

    // Nueva query: Estaciones más afectadas por accidentes en la vía
    // Filtra por tipo 'Accidente en la vía' y cuenta por estación
    @Query("SELECT f.estacion, COUNT(f) FROM Falla f WHERE f.tipo = 'Accidente en la vía' GROUP BY f.estacion ORDER BY COUNT(f) DESC")
    List<Object[]> contarAccidentesPorEstacion();

    // Nueva query: Estaciones más afectadas por congestión
    // Filtra por tipo 'Congestión en la estación' y cuenta por estación
    @Query("SELECT f.estacion, COUNT(f) FROM Falla f WHERE f.tipo = 'Congestión en la estación' GROUP BY f.estacion ORDER BY COUNT(f) DESC")
    List<Object[]> contarCongestionPorEstacion();

    // Nueva query: Hora en la que hay más congestión en el sistema metro
    // Extrae la hora del campo fechaReporte y cuenta las congestiones por hora
    @Query("SELECT HOUR(f.fechaReporte) as hora, COUNT(f) FROM Falla f WHERE f.tipo = 'Congestión en la estación' GROUP BY hora ORDER BY hora ASC")
    List<Object[]> contarCongestionPorHora();

    // Nueva query: Conteo de fallas por tipo (para la gráfica de pie/dona)
    @Query("SELECT f.tipo, COUNT(f) FROM Falla f GROUP BY f.tipo ORDER BY COUNT(f) DESC")
    List<Object[]> contarFallasPorTipo();

    // Nueva query: Conteo total de fallas por estación (para una gráfica general)
    @Query("SELECT f.estacion, COUNT(f) FROM Falla f GROUP BY f.estacion ORDER BY COUNT(f) DESC")
    List<Object[]> contarTodasFallasPorEstacion();
}