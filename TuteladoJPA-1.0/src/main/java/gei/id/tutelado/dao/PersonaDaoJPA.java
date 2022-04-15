package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.model.Museo;
import gei.id.tutelado.model.Persona;
import gei.id.tutelado.model.Socios;
import org.hibernate.LazyInitializationException;


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

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try {
                empleado.getMuseo().getEmpleados();
            } catch (Exception ex2) {
                if (ex2 instanceof LazyInitializationException)

                {

                    empleado = em.merge(empleado);
                    empleado.getMuseo().getEmpleados();

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
    public List<Museo> recuperaMuseos(Long idSocio) {
        List <Museo> museos=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            museos = em.createNamedQuery("Socios.recuperaMuseos", Museo.class)
                    .setParameter("id", idSocio).getResultList();

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
