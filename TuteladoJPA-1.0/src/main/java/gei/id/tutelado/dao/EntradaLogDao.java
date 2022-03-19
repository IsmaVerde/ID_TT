package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Usuario;

public interface EntradaLogDao {
    
	void setup (Configuracion config);
	
	// OPERACIONS CRUD BASICAS
	EntradaLog almacena (EntradaLog log);
	EntradaLog modifica (EntradaLog log);
	void elimina (EntradaLog log);
	EntradaLog recuperaPorCodigo (String codigo);
	
	//QUERIES ADICIONAIS
	List<EntradaLog> recuperaTodasUsuario(Usuario u);

}
