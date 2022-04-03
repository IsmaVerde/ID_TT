package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@TableGenerator(name="generadorIdsMuseo", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idMuseo",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries({
        @NamedQuery(name="Museo.recuperaPorNombre",
                query="SELECT m FROM Museo m where m.nombre=:nombre"),
        @NamedQuery (name="Usuario.recuperaTodos",
                query="SELECT m FROM Museo m ORDER BY m.nombre")
})

@Entity
public class Museo {
    @Id
    @GeneratedValue (generator="generadorIdsMuseos")
    private Long idmuseo;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String categoria;

    @ManyToMany (mappedBy = "museos")
    private Set<Socios> inscritos = new HashSet<Socios>();

    @OneToMany (mappedBy = "museo")
    private Set<Empleados> empleados;

    public Long getIdmuseo() {
        return idmuseo;
    }

    public void setIdmuseo(Long idmuseo) {
        this.idmuseo = idmuseo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<Socios> getInscritos() {
        return inscritos;
    }

    public void setInscritos(Set<Socios> inscritos) {
        this.inscritos = inscritos;
    }

    public Set<Empleados> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Empleados> empleados) {
        this.empleados = empleados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Museo)) return false;
        Museo museo = (Museo) o;
        return nombre.equals(museo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return "Museo{" +
                "idmuseo=" + idmuseo +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", inscritos=" + inscritos +
                ", empleados=" + empleados +
                '}';
    }
}
