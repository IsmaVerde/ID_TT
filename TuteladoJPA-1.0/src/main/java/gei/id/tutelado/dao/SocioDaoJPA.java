package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Museo;
import gei.id.tutelado.model.Socios;
import gei.id.tutelado.model.Socios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class SocioDaoJPA extends PersonaDaoJPA implements SocioDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public List<Socios> recuperaMuseos(Long idSocio) {
        List <Socios> socios=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            socios = em.createNamedQuery("Socios.recuperaMuseos", Socios.class)
                    .setParameter("idSocio", idSocio).getResultList();

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

        return socios;
    }

    @Override
    public Socios recuperaPorDni(Long dni) {
        List<Socios> socios = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            socios = em.createNamedQuery("Socios.recuperaPorDni", Socios.class).setParameter("dni", dni).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return (socios.size() != 0 ? socios.get(0) : null);
    }

    @Override
    public List<Socios> recuperaSociosMinDosMuseos() {
        List <Socios> socios=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            socios = em.createNamedQuery("Socios.recuperaSociosMinDosMuseos", Socios.class).getResultList();

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

        return socios;
    }
}
