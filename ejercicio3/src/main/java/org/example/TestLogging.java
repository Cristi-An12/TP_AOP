package org.example;

import org.example.Model.*;
import org.example.Persistencia.ConcursoRepoTXT;
import org.example.Persistencia.InscripcionRepoTXT;

import java.time.LocalDate;
import java.util.List;

public class TestLogging {
    public static void main(String[] args) {
        ConcursoRepoTXT concursoRepo = new ConcursoRepoTXT("concursos.txt");
        InscripcionRepoTXT inscripcionRepo = new InscripcionRepoTXT("inscripciones.txt");
        
        System.out.println("=== Probando logging de métodos con AOP ===\n");
        
        // Probar el método concursosAbiertos (equivalente a todosLosConcursos)
        System.out.println("1. Llamando a concursosAbiertos()...");
        try {
            List<Concurso> concursos = concursoRepo.concursosAbiertos();
            System.out.println("Concursos encontrados: " + concursos.size());
        } catch (Exception e) {
            System.out.println("Error al leer concursos: " + e.getMessage());
        }
        
        // Crear un concurso de prueba
        Concurso concurso = new Concurso(1, "Concurso de Prueba", 
                                       LocalDate.now(), LocalDate.now().plusDays(30));
        
        Persona persona = new Persona("Cristian", "Millaqueo", "43684408", "123456789", "Cristian@test.com");
        Inscripcion inscripcion = new Inscripcion(persona, concurso);
        
        // Probar el método guardarInscripcion (equivalente a saveInscription)
        try {
            inscripcionRepo.guardarInscripcion(inscripcion);
            System.out.println("Inscripción guardada exitosamente");
        } catch (Exception e) {
            System.out.println("Error al guardar inscripción: " + e.getMessage());
        }
        
        System.out.println("\n=== Revisa el archivo 'application.log' para ver los registros generados ===");
        System.out.println("Formato esperado:");
        System.out.println("\"nombreMetodo\", \"parametros|separados|por|pipe\", \"fecha/hora\"");
        System.out.println("\"nombreMetodo\", \"sin parametros\", \"fecha/hora\"");
    }
} 