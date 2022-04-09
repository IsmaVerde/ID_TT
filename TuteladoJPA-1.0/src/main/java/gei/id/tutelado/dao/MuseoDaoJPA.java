package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.model.Museo;
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

    @Override
    public Museo restauraSocios (Museo museo){

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try {
                museo.getInscritos().size();
            } catch (Exception ex2) {
                if (ex2 instanceof LazyInitializationException)

                {

                    museo = em.merge(museo);
                    museo.getInscritos().size();

                } else {
                    throw ex2;
                }
            }
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

        return (museo);


    }

    @Override
    public Museo restauraEmpleados (Museo museo){

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try {
                museo.getEmpleados().size();
            } catch (Exception ex2) {
                if (ex2 instanceof LazyInitializationException)

                {

                    museo = em.merge(museo);
                    museo.getEmpleados().size();

                } else {
                    throw ex2;
                }
            }
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

        return (museo);


    }

    @Override
    public List<Museo> recuperaExperiencia(){
        List <Museo> museos=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            museos = em.createNamedQuery("Museo.recuperaExperiencia", Museo.class).getResultList();

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

        return museos;
    }
    
}
