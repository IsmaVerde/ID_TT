package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.model.Persona;
import gei.id.tutelado.model.Socios;


public class PersonaDaoJPA implements PersonaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Persona alta(Persona persona) {

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
        return persona;
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

    @Override
    public Empleados recuperaMuseo(Empleados empleado) {
        // Devuelve un objeto empleado con su museos cargado (Si ne estaban cargados ya)

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            empleado = em.merge(empleado);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return (empleado);

    }

    @Override
    public List<Double> recuperaSueldoMedio() {
        List<Double> salarios=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            salarios = em.createNamedQuery("Empleados.recuperaSueldoMedio", Double.class).getResultList();

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

        return salarios;
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
