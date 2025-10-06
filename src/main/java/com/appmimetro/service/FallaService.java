package com.appmimetro.service;

import com.appmimetro.model.Falla;
import com.appmimetro.repository.FallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FallaService {
    @Autowired
    private FallaRepository fallaRepository;

    public Falla guardarFalla(Falla falla) {
        return fallaRepository.save(falla);
    }

    public List<Object[]> obtenerConteoFallasAgrupadas() {
        return fallaRepository.contarFallasAgrupadas();
    }

    public Long contarTotalFallas() {
        return fallaRepository.count();
    }

    public Long contarEstacionesAfectadas() {
        return fallaRepository.contarEstacionesAfectadas();
    }

    public Long contarReportesHoy() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(23, 59, 59);
        return fallaRepository.contarReportesPorRangoFecha(inicioDia, finDia);
    }

    public List<Object[]> obtenerAccidentesPorEstacion() {
        return fallaRepository.contarAccidentesPorEstacion();
    }

    public List<Object[]> obtenerCongestionPorEstacion() {
        return fallaRepository.contarCongestionPorEstacion();
    }

    public List<Object[]> obtenerCongestionPorHora() {
        return fallaRepository.contarCongestionPorHora();
    }

    public List<Object[]> obtenerTiposFalla() {
        return fallaRepository.contarFallasPorTipo();
    }

    public List<Object[]> obtenerTodasFallasPorEstacion() {
        return fallaRepository.contarTodasFallasPorEstacion();
    }
}