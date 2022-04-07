package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persona;

public interface PersonaDao {

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    Persona alta (Persona persona);
    Persona modifica (Persona persona);
    void elimina (Persona persona);
    Persona recuperaPorDni (String dni);

}
