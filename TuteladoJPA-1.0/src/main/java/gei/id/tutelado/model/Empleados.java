package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries({
        //Metodo que utiliza una funcion de agregacion
        @NamedQuery(name="Empleados.recuperaSueldoMedio",
                query="SELECT avg(sueldo) FROM Empleados e"),
        //Recuperacion por clave natural
        @NamedQuery (name="Empleados.recuperaPorDni",
                query="SELECT e FROM Empleados e where e.dni=:dni")
})


@Entity
@Table(name = "t_empleados_tcc")
public class Empleados extends Persona {

    @Column(nullable = false)
    private String puesto;

    @Column(nullable = false)
    private int sueldo;

    @Column()
    private String experiencia;

    @ManyToOne (fetch=FetchType.LAZY, cascade={} )
    @JoinColumn (name="museo_emp", nullable=false)
    private Museo museo;

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public Museo getMuseo() {
        return museo;
    }

    public void setMuseo(Museo museo) {
        this.museo = museo;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + getId() +
                ", dni='" + getDni() + '\'' +
                ", nombre completo='" + getNombrecompleto() + '\'' +
                ", email='" + getEmail() + '\''+
                ", puesto='" + puesto + '\'' +
                ", sueldo=" + sueldo +
                ", experiencia='" + experiencia + '\'' +
                '}';
    }
}
