package org.example.Model;

public class Inscripcion {
    private Persona persona;
    private Concurso concurso;

    public Inscripcion(Persona persona, Concurso concurso) {
        this.persona = persona;
        this.concurso = concurso;
    }

    // Getters
    public Persona getPersona() {
        return persona;
    }

    public Concurso getConcurso() {
        return concurso;
    }

}
