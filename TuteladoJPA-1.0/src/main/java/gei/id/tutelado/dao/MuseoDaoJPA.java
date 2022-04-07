package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.LazyInitializationException;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Museo;


public class MuseoDaoJPA implements MuseoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Museo alta(Museo museo) {

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(museo);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return museo;
    }

    @Override
    public Museo modifica(Museo museo) {

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            museo = em.merge(museo);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return (museo);
    }

    @Override
    public void elimina(Museo museo) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Museo museoTmp = em.find(Museo.class, museo.getIdmuseo());
            em.remove(museoTmp);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
    }


    @Override
    public Museo recuperaPorNombre(String nombre) {
        List<Museo> museos = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            museos = em.createNamedQuery("Museo.recuperaPorNombre", Museo.class).setParameter("nombre", nombre).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return (museos.size() != 0 ? museos.get(0) : null);
    }
}
