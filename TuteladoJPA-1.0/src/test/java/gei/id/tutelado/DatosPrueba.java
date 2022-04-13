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
	
	public Socios s0, s1, s2, s3, s4;
	public List<Socios> listadoS;

	public Empleados e0, e1, e2, e3, e4;
	public List<Empleados> listadoE;
	
	public Museo m0, m1, m2;
	public List<Museo> listadoM;
	
	
	
	public void Setup (Configuracion config) {
		this.emf=(EntityManagerFactory) config.get("EMF");
	}
	
	public void crearSociosSueltos() {
		List<String> desc0 = Arrays.asList("Carnet Joven");
		List<String> desc1 = Arrays.asList();
		List<String> desc2 = Arrays.asList("Carnet Joven", "Familia Numerosa");
		List<String> desc3 = Arrays.asList("Tercera Edad");
		List<String> desc4 = Arrays.asList("Carnet Joven", "Universitario");

/*		Set<Museo> mus0 = new HashSet<Museo>();
		Set<Museo> mus1 = new HashSet<Museo>();
		Set<Museo> mus2 = new HashSet<Museo>();
		Set<Museo> mus3 = new HashSet<Museo>();
		Set<Museo> mus4 = new HashSet<Museo>();

		mus0.add(m2);
		mus0.add(m0);
		mus1.add(m1);
		mus1.add(m0);
		mus2.add(m0);
		mus3.add(m2);
		mus4.add(m0);
		mus4.add(m1);
		mus4.add(m2);*/


		this.s0 = new Socios();
        this.s0.setDni("0000");
        this.s0.setNombrecompleto("Primer Socio");
        this.s0.setEmail("Primero@udc");
		this.s0.setTipo("Turista internacional");
		this.s0.setProfesion("Camarero");
		this.s0.setProcedencia("Francia");
		this.s0.setDescuentos(desc0);
		this.s0.setMuseos(null);

		this.s1 = new Socios();
		this.s1.setDni("1111");
		this.s1.setNombrecompleto("Segundo Socio");
		this.s1.setEmail("Segundo@udc");
		this.s1.setTipo("Residente Local");
		this.s1.setProfesion("Profesor");
		this.s1.setProcedencia("España");
		this.s1.setDescuentos(desc1);
		this.s1.setMuseos(null);

		this.s2 = new Socios();
		this.s2.setDni("2222");
		this.s2.setNombrecompleto("Tercero Socio");
		this.s2.setEmail("Tercero@gmail");
		this.s2.setTipo("Turista Nacional");
		this.s2.setProfesion("Marinero");
		this.s2.setProcedencia("España");
		this.s2.setDescuentos(desc2);
		this.s2.setMuseos(null);

		this.s3 = new Socios();
		this.s3.setDni("3333");
		this.s3.setNombrecompleto("Cuarto Socio");
		this.s3.setEmail("Cuarto@hotmail");
		this.s3.setTipo("Residente local");
		this.s3.setProfesion("Juez");
		this.s3.setProcedencia("España");
		this.s3.setDescuentos(desc3);
		this.s3.setMuseos(null);

		this.s4 = new Socios();
		this.s4.setDni("4444");
		this.s4.setNombrecompleto("Quinto Socio");
		this.s4.setEmail("Quinto@udc");
		this.s4.setTipo("Turista internacional");
		this.s4.setProfesion("Pintor");
		this.s4.setProcedencia("Italia");
		this.s4.setDescuentos(desc4);
		this.s4.setMuseos(null);


        this.listadoS = new ArrayList<Socios> ();
        this.listadoS.add(0,s0);
		this.listadoS.add(1,s1);
		this.listadoS.add(2,s2);
		this.listadoS.add(3,s3);
		this.listadoS.add(4,s4);

	}

	public void crearEmpleadosSueltos() {

		this.e0 = new Empleados();
		this.e0.setDni("0001");
		this.e0.setNombrecompleto("Primer Empleado");
		this.e0.setEmail("Primerempleado@udc");
		this.e0.setPuesto("Guia");
		this.e0.setSueldo(1500);
		this.e0.setExperiencia("5 años");
		this.e0.setMuseo(null);

		this.e1 = new Empleados();
		this.e1.setDni("0002");
		this.e1.setNombrecompleto("Segundo Empleado");
		this.e1.setEmail("Segundoempleado@udc");
		this.e1.setPuesto("Vigilante");
		this.e1.setSueldo(1000);
		this.e1.setExperiencia("1 año");
		this.e1.setMuseo(null);

		this.e2 = new Empleados();
		this.e2.setDni("0003");
		this.e2.setNombrecompleto("Tercer Empleado");
		this.e2.setEmail("Tercerempleado@gmail");
		this.e2.setPuesto("Conserje");
		this.e2.setSueldo(1200);
		this.e2.setExperiencia("10 años");
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

/*
		Set<Socios> i0 = new HashSet<Socios>();
		Set<Socios> i1 = new HashSet<Socios>();
		Set<Socios> i2 = new HashSet<Socios>();

		Set<Empleados> emp0 = new HashSet<Empleados>();
		Set<Empleados> emp1 = new HashSet<Empleados>();
		Set<Empleados> emp2 = new HashSet<Empleados>();
*/

/*
		i0.add(s0);
		i0.add(s1);
		i0.add(s2);
		i0.add(s4);
		i1.add(s1);
		i1.add(s4);
		i2.add(s0);
		i2.add(s3);
		i2.add(s4);

		emp0.add(e0);
		emp0.add(e3);
		emp0.add(e4);
		emp1.add(e1);
		emp2.add(e2);*/



		this.m0 = new Museo();
		this.m0.setNombre("Museo Domus");
		this.m0.setUbicacion("A Corunha");
		this.m0.setCategoria("Ciencias");
		this.m0.setInscritos(null);
		this.m0.setEmpleados(null);

		this.m1 = new Museo();
		this.m1.setNombre("Museo del Greco");
		this.m1.setUbicacion("Toledo");
		this.m1.setCategoria("Casa-Museo");
		this.m1.setInscritos(null);
		this.m1.setEmpleados(null);

		this.m2 = new Museo();
		this.m2.setNombre("Museo del Prado");
		this.m2.setUbicacion("Madrid");
		this.m2.setCategoria("Arte");
		this.m2.setInscritos(null);
		this.m2.setEmpleados(null);

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
			em.createQuery("DELETE FROM Socios").executeUpdate();
			em.createQuery("DELETE FROM Empleados").executeUpdate();
			/*em.createQuery("DELETE FROM Persona").executeUpdate();*/
			em.createQuery("DELETE FROM Museo").executeUpdate();
			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idPersona'" ).executeUpdate();
/*			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idSocios'" ).executeUpdate();
			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idEmpleados'" ).executeUpdate();*/
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
