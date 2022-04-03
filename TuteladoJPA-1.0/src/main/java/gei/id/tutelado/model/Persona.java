package gei.id.tutelado.model;

import java.util.Objects;
import javax.persistence.*;

@TableGenerator(name="generadorIdsPersona", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idPersona",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries ({
        @NamedQuery (name="Persona.recuperaPorDNI",
                query="SELECT p FROM Persona p where p.dni=:dni"),
        @NamedQuery (name="Persona.recuperaTodas",
                query="SELECT p FROM Persona p ORDER BY p.dni")
})

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona{
    @Id
    @GeneratedValue (generator="generadorIdsPersona")
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombrecompleto;

    @Column(unique = true)
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
        return dni.equals(persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
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
