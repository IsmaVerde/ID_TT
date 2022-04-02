package gei.id.tutelado.model;

import java.util.Objects;

public class Persona{
    private Long id;

    private String dni;

    private String nombrecompleto;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return id.equals(persona.id) &&
                dni.equals(persona.dni) &&
                nombrecompleto.equals(persona.nombrecompleto) &&
                email.equals(persona.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni, nombrecompleto, email);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombrecompleto='" + nombrecompleto + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
