package org.example.Persistencia;

import org.example.Model.Concurso;
import org.example.Model.ConcursoRepository;
import org.example.Model.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;


public class ConcursoRepoTXT implements ConcursoRepository {
    private String RutaArchivo;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
    public ConcursoRepoTXT(String rutaArchivo) {
        this.RutaArchivo = rutaArchivo;
    }

    @Override
    @Log
    public List<Concurso> concursosAbiertos() {
        List<Concurso> concursos = new ArrayList<> ();
        try (BufferedReader reader = new BufferedReader (new FileReader (RutaArchivo))) {
            String linea;
            while ((linea = reader.readLine ()) != null) {
                Concurso concurso = parsearConcurso (linea);
                if (concurso.inscripcionAbierta () && concurso != null) {
                    concursos.add (concurso);
                }
            }
        } catch (IOException e) {
            System.err.println ("error al leer el archivo:" + e.getMessage ());
        }
        return concursos;
    }

    private Concurso parsearConcurso (String linea){
        try {
            String[] partes = linea.split (", ");
            if (partes.length == 4){
                int id = Integer.parseInt (partes[0].trim ());
                String nombre = partes[1].trim ();
                LocalDate inicio = LocalDate.parse (partes[2].trim (), FORMATTER);
                LocalDate fin = LocalDate.parse (partes[3].trim (), FORMATTER);
                return new Concurso (id, nombre, inicio, fin);
            }
            }catch (Exception e){
                System.err.println ("Error al parsear linea: + linea" + " - " + e.getMessage ());
        }
        return null;
    }
}

