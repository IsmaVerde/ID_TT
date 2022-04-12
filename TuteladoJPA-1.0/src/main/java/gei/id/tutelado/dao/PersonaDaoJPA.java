package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persona;


public abstract class PersonaDaoJPA implements PersonaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public void alta(Persona persona) {

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(persona);


            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        System.out.println(persona);
    }

    @Override
    public Persona modifica(Persona persona) {

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            persona = em.merge(persona);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return (persona);
    }

    @Override
    public void elimina(Persona persona) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Persona personaTmp = em.find(Persona.class, persona.getId());
            em.remove(personaTmp);

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
    public Persona recuperaPorDni(String dni) {
        List<Persona> personas = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            personas = em.createNamedQuery("Persona.recuperaPorDni", Persona.class).setParameter("dni", dni).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return (personas.size() != 0 ? personas.get(0) : null);
    }


}
