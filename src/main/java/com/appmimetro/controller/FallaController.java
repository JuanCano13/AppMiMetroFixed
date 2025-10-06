package com.appmimetro.controller;

import com.appmimetro.model.Falla;
import com.appmimetro.service.FallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/fallas")
@CrossOrigin(origins = "*")
public class FallaController {
    @Autowired
    private FallaService fallaService;

    // Endpoint para guardar un nuevo reporte de falla
    @PostMapping
    public Falla reportarFalla(@RequestBody Falla falla) {
        return fallaService.guardarFalla(falla);
    }

    // Endpoint para el panel de alertas agrupadas
    @GetMapping("/conteo")
    public List<Map<String, Object>> obtenerConteo() {
        List<Object[]> resultados = fallaService.obtenerConteoFallasAgrupadas();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("estacion", fila[0]);
            item.put("tipo", fila[1]);
            item.put("total", fila[2]);
            respuesta.add(item);
        }

        return respuesta;
    }

    // Endpoint para obtener estadísticas generales
    @GetMapping("/estadisticas")
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalReportes", fallaService.contarTotalFallas());
        estadisticas.put("estacionesAfectadas", fallaService.contarEstacionesAfectadas());
        estadisticas.put("reportesHoy", fallaService.contarReportesHoy());
        return estadisticas;
    }

    // Endpoint para obtener datos de accidentes por estación
    @GetMapping("/graficas/accidentes-por-estacion")
    public List<Map<String, Object>> getAccidentesPorEstacion() {
        List<Object[]> resultados = fallaService.obtenerAccidentesPorEstacion();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("estacion", fila[0]);
            item.put("total", fila[1]);
            respuesta.add(item);
        }

        return respuesta;
    }

    // Endpoint para obtener datos de congestión por estación
    @GetMapping("/graficas/congestion-por-estacion")
    public List<Map<String, Object>> getCongestionPorEstacion() {
        List<Object[]> resultados = fallaService.obtenerCongestionPorEstacion();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("estacion", fila[0]);
            item.put("total", fila[1]);
            respuesta.add(item);
        }

        return respuesta;
    }

    // Endpoint para obtener datos de congestión por hora
    @GetMapping("/graficas/congestion-hora")
    public List<Map<String, Object>> getCongestionPorHora() {
        List<Object[]> resultados = fallaService.obtenerCongestionPorHora();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("hora", fila[0]);
            item.put("total", fila[1]);
            respuesta.add(item);
        }

        return respuesta;
    }

    // Endpoint para obtener datos de tipos de falla
    @GetMapping("/graficas/tipos-falla")
    public List<Map<String, Object>> getTiposFalla() {
        List<Object[]> resultados = fallaService.obtenerTiposFalla();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("tipo", fila[0]);
            item.put("total", fila[1]);
            respuesta.add(item);
        }

        return respuesta;
    }

    // Endpoint para obtener el total de fallas por estación
    @GetMapping("/graficas/fallas-por-estacion")
    public List<Map<String, Object>> getFallasPorEstacion() {
        List<Object[]> resultados = fallaService.obtenerTodasFallasPorEstacion();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("estacion", fila[0]);
            item.put("total", fila[1]);
            respuesta.add(item);
        }

        return respuesta;
    }
}