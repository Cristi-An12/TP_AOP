package org.example.Model;

import java.time.LocalDate;

public class Concurso {
    private int id;
    private String nombre;
    private LocalDate fechaInicioInscripcion;
    private LocalDate fechaFinInscripcion;

    public Concurso(int id, String nombre, LocalDate fechaInicioInscripcion, LocalDate fechaFinInscripcion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public boolean inscripcionAbierta() {
        LocalDate hoy = LocalDate.now();
        return hoy.isAfter(fechaInicioInscripcion.minusDays(1)) &&
                hoy.isBefore(fechaFinInscripcion.plusDays(1));
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaInicioInscripcion() { return fechaInicioInscripcion; }
    public LocalDate getFechaFinInscripcion() { return fechaFinInscripcion; }


    @Override
    public String toString() {
        return nombre;
    }
}
