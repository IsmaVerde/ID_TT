package gei.id.tutelado.model;

import java.util.List;
import java.util.Objects;

public class Museo {

    private Long idmuseo;

    private String nombre;

    private String ubicacion;

    private String categoria;

    private List<Socios> inscritos;

    private List<Empleados> empleados;

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

    public List<Socios> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Socios> inscritos) {
        this.inscritos = inscritos;
    }

    public List<Empleados> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleados> empleados) {
        this.empleados = empleados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Museo)) return false;
        Museo museo = (Museo) o;
        return idmuseo.equals(museo.idmuseo) &&
                nombre.equals(museo.nombre) &&
                ubicacion.equals(museo.ubicacion) &&
                Objects.equals(categoria, museo.categoria) &&
                Objects.equals(inscritos, museo.inscritos) &&
                Objects.equals(empleados, museo.empleados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idmuseo, nombre, ubicacion, categoria, inscritos, empleados);
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
