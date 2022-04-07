package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class EmpleadoDaoJPA extends PersonaDaoJPA implements EmpleadoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
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
    public List<Empleados> recuperaTodos(Empleados empleado) {
        List <Empleados> empleados=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            empleados = em.createNamedQuery("Empleados.recuperaTodos", Empleados.class).getResultList();

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

        return empleados;
    }
}


