package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Usuario;

public interface UsuarioDao {
    	
	void setup (Configuracion config);
	
	// OPERACIONS CRUD BASICAS
	Usuario almacena (Usuario user);
	Usuario modifica (Usuario user);
	void elimina (Usuario user);	
	Usuario recuperaPorNif (String nif);
	
	// OPERACIONS POR ATRIBUTOS LAZY
	Usuario restauraEntradasLog (Usuario user);   
		// Recibe un usuario coa colección de entradas de log como proxy SEN INICIALIZAR
		// Devolve unha copia do usuario coa colección de entradas de log INICIALIZADA
	
	//QUERIES ADICIONAIS
	List<Usuario> recuperaTodos();
}
