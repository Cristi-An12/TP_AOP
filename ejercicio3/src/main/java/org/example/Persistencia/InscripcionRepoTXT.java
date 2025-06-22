package org.example.Persistencia;

import org.example.Model.Inscripcion;
import org.example.Model.InscripcionRepository;
import org.example.Model.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InscripcionRepoTXT implements InscripcionRepository {
    private String rutaArchivo;

    public InscripcionRepoTXT(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    @Log
    public void guardarInscripcion (Inscripcion inscripcion) {
        try(PrintWriter writer = new PrintWriter(new FileWriter (rutaArchivo, true))) {
            String linea = formatearInscripcion(inscripcion);
            writer.println (linea);
        }catch (IOException e){
            throw new RuntimeException ("Error al guardar inscripcion: "+ e.getMessage ());
        }
    }
    private  String formatearInscripcion (Inscripcion inscripcion) {
        return String.format("%s,%s,%s,%s,%s,%s",
                inscripcion.getConcurso().getId(),
                inscripcion.getConcurso().getNombre(),
                inscripcion.getPersona().getNombre(),
                inscripcion.getPersona().getApellido(),
                inscripcion.getPersona().getDni(),
                inscripcion.getPersona().getEmail());
    }
}
