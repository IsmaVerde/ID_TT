package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Museo;
import gei.id.tutelado.model.Socios;

public interface MuseoDao {

    void setup (Configuracion config);

    // OPERACIONES CRUD BASICAS
    Museo alta (Museo museo);
    Museo modifica (Museo museo);
    void elimina (Museo museo);
    Museo recuperaPorNombre (String nombre);
    Museo restauraEmpleados (Museo museo);
    Museo restauraSocios (Museo museo);
    List<Socios> recuperaSociosSinMuseo();

}
