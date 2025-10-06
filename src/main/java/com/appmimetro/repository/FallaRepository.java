package com.appmimetro.repository;

import com.appmimetro.model.Falla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FallaRepository extends JpaRepository<Falla, Long> {
    // Query para el panel de alertas agrupadas
    @Query("SELECT f.estacion, f.tipo, COUNT(f) as total FROM Falla f GROUP BY f.estacion, f.tipo ORDER BY total DESC")
    List<Object[]> contarFallasAgrupadas();

    // Contar estaciones afectadas
    @Query("SELECT COUNT(DISTINCT f.estacion) FROM Falla f")
    Long contarEstacionesAfectadas();

    // Contar reportes por rango de fechas
    @Query("SELECT COUNT(f) FROM Falla f WHERE f.fechaReporte BETWEEN :inicio AND :fin")
    Long contarReportesPorRangoFecha(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // Gráfica: Accidentes por estación
    @Query("SELECT f.estacion, COUNT(f) FROM Falla f WHERE f.tipo = 'Accidente en la vía' GROUP BY f.estacion ORDER BY COUNT(f) DESC")
    List<Object[]> contarAccidentesPorEstacion();

    // Gráfica: Congestión por estación
    @Query("SELECT f.estacion, COUNT(f) FROM Falla f WHERE f.tipo = 'Congestión en la estación' GROUP BY f.estacion ORDER BY COUNT(f) DESC")
    List<Object[]> contarCongestionPorEstacion();

    // Gráfica: Congestión por hora (CORREGIDO: fecha_reporte sin 's')
    @Query(value = "SELECT HOUR(f.fecha_reporte) as hora, COUNT(*) as total FROM fallas f WHERE f.tipo = 'Congestión en la estación' GROUP BY hora ORDER BY hora ASC", nativeQuery = true)
    List<Object[]> contarCongestionPorHora();

    // Gráfica: Tipos de falla
    @Query("SELECT f.tipo, COUNT(f) FROM Falla f GROUP BY f.tipo ORDER BY COUNT(f) DESC")
    List<Object[]> contarFallasPorTipo();

    // Gráfica: Total fallas por estación
    @Query("SELECT f.estacion, COUNT(f) FROM Falla f GROUP BY f.estacion ORDER BY COUNT(f) DESC")
    List<Object[]> contarTodasFallasPorEstacion();
}