package com.appmimetro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist; // Importa PrePersist
import jakarta.persistence.Table;
import java.time.LocalDateTime; // Importa LocalDateTime

@Entity
@Table(name = "fallas")
public class Falla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estacion;
    private String tipo;
    private String detalles;

    // ESTE ES EL CAMPO CLAVE QUE FALTABA
    @Column(name = "fecha_reporte", nullable = false, updatable = false)
    private LocalDateTime fechaReporte;

    public Falla() {}

    public Falla(String estacion, String tipo, String detalles) {
        this.estacion = estacion;
        this.tipo = tipo;
        this.detalles = detalles;
    }

    // ESTE MÉTODO SE EJECUTA ANTES DE QUE SE GUARDE LA ENTIDAD
    @PrePersist
    protected void onCreate() {
        fechaReporte = LocalDateTime.now(); // Asigna la fecha y hora actual automáticamente
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

    // NUEVOS GETTER Y SETTER PARA fechaReporte
    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
}