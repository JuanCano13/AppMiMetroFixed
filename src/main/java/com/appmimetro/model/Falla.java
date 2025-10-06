package com.appmimetro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fallas")
public class Falla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estacion;
    private String tipo;
    private String detalles;

    @Column(name = "fecha_reporte")  // <-- CORREGIDO: sin 's' al final
    private LocalDateTime fechaReporte;

    // Constructor vacío
    public Falla() {}

    // Constructor con parámetros
    public Falla(String estacion, String tipo, String detalles) {
        this.estacion = estacion;
        this.tipo = tipo;
        this.detalles = detalles;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    // Para que la fecha se asigne automáticamente al guardar
    @PrePersist
    protected void onCreate() {
        if (fechaReporte == null) {
            fechaReporte = LocalDateTime.now();
        }
    }
}