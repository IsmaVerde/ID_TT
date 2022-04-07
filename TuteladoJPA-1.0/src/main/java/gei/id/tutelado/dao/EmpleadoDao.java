package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;

import java.util.List;

public interface EmpleadoDao extends PersonaDao {

    void setup (Configuracion config);

    Empleados recuperaMuseo (Empleados empleado);
    List<Empleados> recuperaTodos(Empleados empleado);


}
