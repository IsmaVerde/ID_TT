package gei.id.tutelado.model;

import java.util.List;
import java.util.Objects;

public class Socios extends Persona {

    private String tipo;

    private String profesion;

    private String procedencia;

    private List<String> descuentos;

    private List<Museo> museos;

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

    public List<Museo> getMuseos() {
        return museos;
    }

    public void setMuseos(List<Museo> museos) {
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
