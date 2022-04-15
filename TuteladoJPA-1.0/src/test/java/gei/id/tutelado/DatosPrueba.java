package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.model.Socios;
import gei.id.tutelado.model.Museo;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DatosPrueba {

	private EntityManagerFactory emf=null;
	
	public Socios s0, s1, s2, s3, s4, s5;
	public List<Socios> listadoS;

	public Empleados e0, e1, e2, e3, e4;
	public List<Empleados> listadoE;
	
	public Museo m0, m1, m2;
	public List<Museo> listadoM;
	
	
	
	public void Setup (Configuracion config) {
		this.emf=(EntityManagerFactory) config.get("EMF");
	}
	
	public void crearSociosSueltos() {

		Set<String> desc0 = new HashSet<String>(){{ add("Carnet Joven"); }};

		Set<String> desc1 = new HashSet<String>(){{}};

		Set<String> desc2 = new HashSet<String>(){{ add("Carnet Joven"); add("Familia Numerosa"); }};

		Set<String> desc3 = new HashSet<String>(){{ add("Tercera Edad"); }};

		Set<String> desc4 = new HashSet<String>(){{ add("Carnet Joven"); add("Universitario"); }};

		this.s0 = new Socios();
        this.s0.setDni("0000");
        this.s0.setNombrecompleto("Primer Socio");
        this.s0.setEmail("Primero@udc");
		this.s0.setTipo("Turista internacional");
		this.s0.setProfesion("Camarero");
		this.s0.setProcedencia("Francia");
		this.s0.setDescuentos(desc0);

		this.s1 = new Socios();
		this.s1.setDni("1111");
		this.s1.setNombrecompleto("Segundo Socio");
		this.s1.setEmail("Segundo@udc");
		this.s1.setTipo("Residente Local");
		this.s1.setProfesion("Profesor");
		this.s1.setProcedencia("España");
		this.s1.setDescuentos(desc1);

		this.s2 = new Socios();
		this.s2.setDni("2222");
		this.s2.setNombrecompleto("Tercero Socio");
		this.s2.setEmail("Tercero@gmail");
		this.s2.setTipo("Turista Nacional");
		this.s2.setProfesion("Marinero");
		this.s2.setProcedencia("España");
		this.s2.setDescuentos(desc2);

		this.s3 = new Socios();
		this.s3.setDni("3333");
		this.s3.setNombrecompleto("Cuarto Socio");
		this.s3.setEmail("Cuarto@hotmail");
		this.s3.setTipo("Residente local");
		this.s3.setProfesion("Juez");
		this.s3.setProcedencia("España");
		this.s3.setDescuentos(desc3);

		this.s4 = new Socios();
		this.s4.setDni("4444");
		this.s4.setNombrecompleto("Quinto Socio");
		this.s4.setEmail("Quinto@udc");
		this.s4.setTipo("Turista internacional");
		this.s4.setProfesion("Pintor");
		this.s4.setProcedencia("Italia");
		this.s4.setDescuentos(desc4);

		this.s5 = new Socios();
		this.s5.setDni("5555");
		this.s5.setNombrecompleto("Sexto Socio");
		this.s5.setEmail("Sexto@udc");
		this.s5.setTipo("Turista nacional");
		this.s5.setProfesion("Medico");
		this.s5.setProcedencia("Alemania");

        this.listadoS = new ArrayList<Socios> ();
        this.listadoS.add(0,s0);
		this.listadoS.add(1,s1);
		this.listadoS.add(2,s2);
		this.listadoS.add(3,s3);
		this.listadoS.add(4,s4);
		this.listadoS.add(5,s5);
	}

	public void crearEmpleadosSueltos() {

		this.e0 = new Empleados();
		this.e0.setDni("0001");
		this.e0.setNombrecompleto("Primer Empleado");
		this.e0.setEmail("Primerempleado@udc");
		this.e0.setPuesto("Guia");
		this.e0.setSueldo(1500);
		this.e0.setExperiencia("5 anhos");
		this.e0.setMuseo(null);

		this.e1 = new Empleados();
		this.e1.setDni("0002");
		this.e1.setNombrecompleto("Segundo Empleado");
		this.e1.setEmail("Segundoempleado@udc");
		this.e1.setPuesto("Vigilante");
		this.e1.setSueldo(1000);
		this.e1.setExperiencia("1 anho");
		this.e1.setMuseo(null);

		this.e2 = new Empleados();
		this.e2.setDni("0003");
		this.e2.setNombrecompleto("Tercer Empleado");
		this.e2.setEmail("Tercerempleado@gmail");
		this.e2.setPuesto("Conserje");
		this.e2.setSueldo(1200);
		this.e2.setExperiencia("10 anhos");
		this.e2.setMuseo(null);

		this.e3 = new Empleados();
		this.e3.setDni("0004");
		this.e3.setNombrecompleto("Cuarto Empleado");
		this.e3.setEmail("Cuartempleadoo@hotmail");
		this.e3.setPuesto("Restaurador");
		this.e3.setSueldo(2000);
		this.e3.setExperiencia(null);
		this.e3.setMuseo(null);

		this.e4 = new Empleados();
		this.e4.setDni("0005");
		this.e4.setNombrecompleto("Quinto Empleado");
		this.e4.setEmail("Quintoempleado@udc");
		this.e4.setPuesto("Guia");
		this.e4.setSueldo(1700);
		this.e4.setExperiencia("20 años");
		this.e4.setMuseo(null);


		this.listadoE = new ArrayList<Empleados> ();
		this.listadoE.add(0,e0);
		this.listadoE.add(1,e1);
		this.listadoE.add(2,e2);
		this.listadoE.add(3,e3);
		this.listadoE.add(4,e4);

	}

	public void crearMuseosSueltos() {

		this.m0 = new Museo();
		this.m0.setNombre("Museo Domus");
		this.m0.setUbicacion("A Corunha");
		this.m0.setCategoria("Ciencias");

		this.m1 = new Museo();
		this.m1.setNombre("Museo del Greco");
		this.m1.setUbicacion("Toledo");
		this.m1.setCategoria("Casa-Museo");

		this.m2 = new Museo();
		this.m2.setNombre("Museo del Prado");
		this.m2.setUbicacion("Madrid");
		this.m2.setCategoria("Arte");

		this.listadoM = new ArrayList<Museo> ();
		this.listadoM.add(0,m0);
		this.listadoM.add(1,m1);
		this.listadoM.add(2,m2);

	}

	public void creaMuseoconInscritos () {
		this.crearMuseosSueltos();
		this.crearSociosSueltos();

		this.m0.agregarSocios(this.s0);
		this.m0.agregarSocios(this.s1);
		this.m0.agregarSocios(this.s2);
		this.m0.agregarSocios(this.s4);

		this.m1.agregarSocios(this.s1);
		this.m1.agregarSocios(this.s4);

		this.m2.agregarSocios(this.s0);
		this.m2.agregarSocios(this.s3);
	}

	public void creaMuseoconEmpleados () {
		this.crearMuseosSueltos();
		this.crearEmpleadosSueltos();

		this.m0.agregarEmpleado(this.e0);
		this.m0.agregarEmpleado(this.e3);
		this.m0.agregarEmpleado(this.e4);

		this.m1.agregarEmpleado(this.e1);

		this.m2.agregarEmpleado(this.e2);
	}

	public void creaMuseoCompleto () {
		creaMuseoconInscritos();
		creaMuseoconEmpleados();
	}


	public void grabaSocios() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Socios> itS = this.listadoS.iterator();
			while (itS.hasNext()) {
				Socios s = itS.next();
				em.persist(s);
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}	
	}

	public void grabaEmpleados() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Empleados> itE = this.listadoE.iterator();
			while (itE.hasNext()) {
				Empleados e = itE.next();
				em.persist(e);
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}
	}

	public void grabaMuseos() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Museo> itM = this.listadoM.iterator();
			while (itM.hasNext()) {
				Museo m = itM.next();
				em.persist(m);
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}
	}
	
	public void limpaBD () {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator <Socios> itS = ((List<Socios>)em.createQuery("FROM Socios").getResultList()).iterator();
			while (itS.hasNext()) em.remove(itS.next());

			em.createQuery("DELETE FROM Socios").executeUpdate();
			em.createQuery("DELETE FROM Empleados").executeUpdate();
			em.createQuery("DELETE FROM Museo").executeUpdate();

			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idPersona'" ).executeUpdate();
			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idMuseo'" ).executeUpdate();

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}
	}
	
	
}
