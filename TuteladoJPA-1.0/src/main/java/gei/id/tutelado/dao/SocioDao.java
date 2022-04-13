package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.model.Museo;
import gei.id.tutelado.model.Socios;

import java.util.List;

public interface SocioDao extends PersonaDao {

    void setup (Configuracion config);

    List<Socios> recuperaMuseos(Long socio);
    Socios recuperaPorDni(Long dni);
    List<Socios> recuperaSociosMinDosMuseos();

}
