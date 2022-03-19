package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Usuario;

public class EntradaLogDaoJPA implements EntradaLogDao {

	private EntityManagerFactory emf; 
	private EntityManager em;
    
	@Override
	public void setup (Configuracion config) {
		this.emf = (EntityManagerFactory) config.get("EMF");
	}
	

	@Override
	public EntradaLog almacena(EntradaLog log) {
		try {
				
			em = emf.createEntityManager();
			em.getTransaction().begin();

			em.persist(log);

			em.getTransaction().commit();
			em.close();
		
		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return log;
	}

	@Override
	public EntradaLog modifica(EntradaLog log) {
		try {

			em = emf.createEntityManager();		
			em.getTransaction().begin();

			log = em.merge (log);

			em.getTransaction().commit();
			em.close();
			
		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return log;
	}

	@Override
	public void elimina(EntradaLog log) {
		try {

			em = emf.createEntityManager();
			em.getTransaction().begin();

			EntradaLog logTmp = em.find (EntradaLog.class, log.getId());
			em.remove (logTmp);

			em.getTransaction().commit();
			em.close();
			
		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
	}

	@Override
	public EntradaLog recuperaPorCodigo(String codigo) {

		List<EntradaLog> entradas=null;
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			entradas = em.createNamedQuery("EntradaLog.recuperaPorCodigo", EntradaLog.class)
					.setParameter("codigo", codigo).getResultList(); 

			em.getTransaction().commit();
			em.close();
		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (entradas.size()==0?null:entradas.get(0));
	}

	
	@Override
	public List<EntradaLog> recuperaTodasUsuario(Usuario u) {
		List <EntradaLog> entradas=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			entradas = em.createNamedQuery("EntradaLog.recuperaTodasUsuario", EntradaLog.class).setParameter("u", u).getResultList(); 

			em.getTransaction().commit();
			em.close();	

		}
		catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}

		return entradas;
	}
}
