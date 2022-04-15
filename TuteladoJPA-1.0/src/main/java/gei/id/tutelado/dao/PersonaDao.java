package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.model.Museo;
import gei.id.tutelado.model.Persona;
import gei.id.tutelado.model.Socios;

import java.util.List;

public interface PersonaDao {

    void setup (Configuracion config);

    // OPERACIONES CRUD BASICAS
    Persona alta (Persona persona);
    Persona modifica (Persona persona);
    void elimina (Persona persona);
    Persona recuperaPorDni (String dni);

    List<Museo> recuperaMuseos(Long socio);


    List<Socios> recuperaSociosMinDosMuseos();

    Empleados recuperaMuseo (Empleados empleado);


    List<Double> recuperaSueldoMedio();

}
