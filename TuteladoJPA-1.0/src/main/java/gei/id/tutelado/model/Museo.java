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
        //Metodo que utiliza un outer join
        @NamedQuery(name="Museo.recuperaSociosSinMuseo",
                query="SELECT s FROM Museo m RIGHT OUTER JOIN m.inscritos s"),
        //Recuperacion por clave natural
        @NamedQuery (name="Museo.recuperaPorNombre",
                query="SELECT m FROM Museo m where m.nombre=:nombre")
})

@Entity
public class Museo {
    @Id
    @GeneratedValue (generator="generadorIdsMuseo")
    private Long idmuseo;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String categoria;

    @ManyToMany (mappedBy = "museos", fetch=FetchType.LAZY, cascade={})
    private Set<Socios> inscritos = new HashSet<Socios>();

    @OneToMany (mappedBy = "museo" , fetch=FetchType.LAZY, cascade={CascadeType.REMOVE})
    private Set<Empleados> Empleadosmuseo = new HashSet<Empleados>();

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
        return Empleadosmuseo;
    }

    public void setEmpleados(Set<Empleados> Empleadosmuseo) {
        this.Empleadosmuseo = Empleadosmuseo;
    }

    public void agregarSocios(Socios socios) {
        Set<Museo> mus =socios.getMuseos();
        mus.add(this);
        socios.setMuseos(mus);
        this.inscritos.add(socios);
    }

    public void agregarEmpleado(Empleados empleados) {
        if (empleados.getMuseo() != null) throw new RuntimeException ("");
        empleados.setMuseo(this);
        this.Empleadosmuseo.add(empleados);
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
                '}';
    }
}
