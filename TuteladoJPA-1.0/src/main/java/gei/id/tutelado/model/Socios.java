package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@TableGenerator(name="GeneradorIdSocios", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idSocio",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries ({
        @NamedQuery (name="Socios.recuperaMuseos",
                query="SELECT s,m FROM Socios s INNER JOIN s.museos m where s.idMuseo=:idMuseo"),
        @NamedQuery (name="Socios.recuperaSociosMinDosMuseos",
                query="SELECT s FROM Socios s where (SELECT COUNT(m) FROM s.museos m) >=2")
})

@Entity
@Table(name = "t_socios_tcc")
public class Socios extends Persona {

    @Column(nullable = false)
    private String tipo;

    @Column()
    private String profesion;

    @Column(nullable = false)
    private String procedencia;

    @ElementCollection
    @CollectionTable (name="t_soc_descuento",joinColumns=@JoinColumn(name="id_soc",nullable=false))
    @Column(name="descuento",nullable = false)
    private List<String> descuentos;

    @ManyToMany (cascade={}, fetch=FetchType.EAGER)
    @JoinTable(name = "socio_museo",
            joinColumns = @JoinColumn(name = "id_socio"),
            inverseJoinColumns = @JoinColumn(name = "id_museo"))
    private Set<Museo> museos = new HashSet<Museo>();

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public List<String> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<String> descuentos) {
        this.descuentos = descuentos;
    }

    public Set<Museo> getMuseos() {
        return museos;
    }

    public void setMuseos(Set<Museo> museos) {
        this.museos = museos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Socios)) return false;
        if (!super.equals(o)) return false;
        Socios socios = (Socios) o;
        return Objects.equals(tipo, socios.tipo) &&
                Objects.equals(profesion, socios.profesion) &&
                Objects.equals(procedencia, socios.procedencia) &&
                Objects.equals(descuentos, socios.descuentos) &&
                museos.equals(socios.museos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipo, profesion, procedencia, descuentos, museos);
    }
}
