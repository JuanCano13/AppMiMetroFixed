package com.appmimetro.service;

import com.appmimetro.model.Falla;
import com.appmimetro.repository.FallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FallaService {

    @Autowired
    private FallaRepository fallaRepository;

    public Falla guardarFalla(Falla falla) {
        return fallaRepository.save(falla);
    }

    // Método para el panel de alertas agrupadas
    public List<Object[]> obtenerConteoFallasAgrupadas() {
        return fallaRepository.contarFallasAgrupadas();
    }

    // Nuevo método de servicio para la gráfica de accidentes por estación
    public List<Object[]> obtenerAccidentesPorEstacion() {
        return fallaRepository.contarAccidentesPorEstacion();
    }

    // Nuevo método de servicio para la gráfica de congestión por estación
    public List<Object[]> obtenerCongestionPorEstacion() {
        return fallaRepository.contarCongestionPorEstacion();
    }

    // Nuevo método de servicio para la gráfica de congestión por hora
    public List<Object[]> obtenerCongestionPorHora() {
        return fallaRepository.contarCongestionPorHora();
    }

    // Nuevo método de servicio para la gráfica de tipos de falla
    public List<Object[]> obtenerConteoFallasPorTipo() {
        return fallaRepository.contarFallasPorTipo();
    }

    // Nuevo método de servicio para la gráfica de fallas totales por estación
    public List<Object[]> obtenerTodasFallasPorEstacion() {
        return fallaRepository.contarTodasFallasPorEstacion();
    }
}