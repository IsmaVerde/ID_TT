package gei.id.tutelado.model;

import java.util.Objects;

public class Empleados extends Persona {

    private String puesto;

    private int sueldo;

    private String experiencia;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleados)) return false;
        Empleados empleados = (Empleados) o;
        return sueldo == empleados.sueldo &&
                Objects.equals(puesto, empleados.puesto) &&
                Objects.equals(experiencia, empleados.experiencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(puesto, sueldo, experiencia);
    }

    @Override
    public String toString() {
        return "Empleados{" +
                "puesto='" + puesto + '\'' +
                ", sueldo=" + sueldo +
                ", experiencia='" + experiencia + '\'' +
                '}';
    }
}
