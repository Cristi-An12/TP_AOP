package org.example.Model;

import java.util.List;

public class ConcursoService {
    private ConcursoRepository concursoRepository;
    private InscripcionRepository inscripcionRepository;

    public ConcursoService(ConcursoRepository concursoRepository, InscripcionRepository inscripcionRepository) {
        this.concursoRepository = concursoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }
    public List<Concurso> obtenerConcursosDisponibles(){
        return concursoRepository.concursosAbiertos();
    }
    public void inscribirPersona(String Nombre, String Apellido, String Dni, String Telefono, String Email, Concurso concurso) {
        Persona persona = new Persona(Nombre, Apellido, Dni, Telefono, Email);

        Inscripcion inscripcion = new Inscripcion(persona, concurso);
        inscripcionRepository.guardarInscripcion(inscripcion);
    }
}
