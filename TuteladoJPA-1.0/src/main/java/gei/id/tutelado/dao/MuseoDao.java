package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Museo;

public interface MuseoDao {

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    Museo alta (Museo museo);
    Museo modifica (Museo museo);
    void elimina (Museo museo);
    Museo recuperaPorNombre (String nombre);

}
