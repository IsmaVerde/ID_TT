package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.MuseoDao;
import gei.id.tutelado.dao.MuseoDaoJPA;
import gei.id.tutelado.dao.PersonaDao;
import gei.id.tutelado.dao.PersonaDaoJPA;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.model.Museo;
import gei.id.tutelado.model.Socios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.util.HashSet;
import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test2_Museos_Empleados {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static DatosPrueba produtorDatos = new DatosPrueba();
    
    private static Configuracion cfg;
    private static MuseoDao musDao;
    private static PersonaDao empDao;
    
    @Rule
    public TestRule watcher = new TestWatcher() {
       protected void starting(Description description) {
    	   log.info("");
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	   log.info("Iniciando test: " + description.getMethodName());
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
       }
       protected void finished(Description description) {
    	   log.info("");
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
    	   log.info("Finalizado test: " + description.getMethodName());
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
       }
    };
    
    
    @BeforeClass
    public static void init() throws Exception {
    	cfg = new ConfiguracionJPA();
    	cfg.start();

    	musDao = new MuseoDaoJPA();
    	empDao = new PersonaDaoJPA();
    	musDao.setup(cfg);
    	empDao.setup(cfg);
    	
    	produtorDatos = new DatosPrueba();
    	produtorDatos.Setup(cfg);
    }
    
    @AfterClass
    public static void endclose() throws Exception {
    	cfg.endUp();    	
    }
    
    
    
	@Before
	public void setUp() throws Exception {		
		log.info("");	
		log.info("Limpando BD -----------------------------------------------------------------------------------------------------");
		produtorDatos.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}	


    @Test
    public void t1_CRUD_Testalta() {


    	log.info("");	
		log.info("Configurando situacion de partida do test -----------------------------------------------------------------------");

		produtorDatos.crearEmpleadosSueltos();
		produtorDatos.crearMuseosSueltos();
		produtorDatos.grabaMuseos();

		log.info("");
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba da grabacion de empleados sueltos\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Primer empleado vinculado a un museo\n"
    			+ "\t\t\t\t b) Nuevo museo para un museo con empleados previos\n");     	

    	// Situacion de partida:
    	// m1 desligado    	
    	// e0, e1 transitorios

    	produtorDatos.m1.agregarEmpleado(produtorDatos.e0);
		
    	log.info("");	
		log.info("Gravando primeira entrada de log dun usuario --------------------------------------------------------------------");
    	Assert.assertNull(produtorDatos.e0.getId());
    	empDao.alta(produtorDatos.e0);
    	Assert.assertNotNull(produtorDatos.e0.getId());
		Assert.assertTrue(produtorDatos.m1.getEmpleados().contains(produtorDatos.e0));

    	produtorDatos.m1.agregarEmpleado(produtorDatos.e1);

    	log.info("");	
		log.info("Gravando segunda entrada de log dun usuario ---------------------------------------------------------------------");
    	Assert.assertNull(produtorDatos.e1.getId());
    	empDao.alta(produtorDatos.e1);
    	Assert.assertNotNull(produtorDatos.e1.getId());
		Assert.assertTrue(produtorDatos.m1.getEmpleados().contains(produtorDatos.e1));
    }

    @Test 
    public void t2a_CRUD_TestRecupera() {
   	
    	Empleados e;
    	
    	log.info("");	
		log.info("Configurando situacion de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaMuseoconEmpleados();
		produtorDatos.grabaMuseos();
    	produtorDatos.grabaEmpleados();


		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba da recuperacion (por codigo) de empleados sueltos\n"
		+ "\t\t\t\t Casos contemplados:\n"
		+ "\t\t\t\t a) Recuperacion por dni existente\n"
		+ "\t\t\t\t b) Recuperacion por dni inexistente\n");

    	// Situacion de partida:
    	// m1, e0, e1 desligados
    	
		log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------");

    	e = (Empleados) empDao.recuperaPorDni(produtorDatos.e0.getDni());

    	Assert.assertEquals (produtorDatos.e0.getDni(),    e.getDni());
    	Assert.assertEquals (produtorDatos.e0.getEmail(), e.getEmail());
    	Assert.assertEquals (produtorDatos.e0.getNombrecompleto(),   e.getNombrecompleto());

    	log.info("");	
		log.info("Probando recuperacion por dni INEXISTENTE --------------------------------------------------");
    	
    	e = (Empleados) empDao.recuperaPorDni("iwbvyhuebvuwebvi");
    	Assert.assertNull (e);

    } 	

    @Test
    public void t2b_CRUD_TestRecupera() {
    	
    	Museo m;
    	Empleados e ,e1;
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situacion de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaMuseoconEmpleados();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaEmpleados();

		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba da recuperacion de propiedades LAZY\n"
		+ "\t\t\t\t Casos contemplados:\n"
		+ "\t\t\t\t a) Recuperacion de museo con coleccion (LAZY) de empleados \n"
		+ "\t\t\t\t b) Carga forzada de coleccion LAZY da dicha coleccion\n"
		+ "\t\t\t\t c) Carga forzada de coleccion LAZY de museo \n");

    	// Situacion de partida:
    	// m1, e0, e1 desligados
    	
		log.info("Probando (excepcion tras) recuperacion LAZY ---------------------------------------------------------------------");
    	
    	m = musDao.recuperaPorNombre(produtorDatos.m1.getNombre());

		log.info("Acceso a entradas de log de usuario");
    	try	{

			Assert.assertEquals(1, m.getEmpleados().size());
			Assert.assertTrue(m.getEmpleados().contains(produtorDatos.e1));
        	excepcion=false;
    	} catch (LazyInitializationException ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	};    	
    	Assert.assertTrue(excepcion);
    
    	log.info("");
    	log.info("Probando carga forzada de coleccion LAZY ------------------------------------------------------------------------");
    	
    	m = musDao.recuperaPorNombre((produtorDatos.m1.getNombre()));
    	m = musDao.restauraEmpleados(m);						// Museo m con proxy ya inicializado
    	
    	Assert.assertEquals(1, m.getEmpleados().size());
		Assert.assertTrue(m.getEmpleados().contains(produtorDatos.e1));

		log.info("");
		log.info("Probando carga forzada de coleccion LAZY ------------------------------------------------------------------------");

		e1 = (Empleados) empDao.recuperaPorDni((produtorDatos.e0.getDni()));
		e1 = empDao.recuperaMuseo(e1);						// Empleado e con proxy ya inicializado

		System.out.println(e1.getMuseo().getNombre());
		Assert.assertTrue(m.getEmpleados().contains(produtorDatos.e1));
	}

    @Test 
    public void t3a_CRUD_TestElimina() {
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

    	produtorDatos.creaMuseoconEmpleados();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaEmpleados();



		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de eliminacion de empleados sueltos (asignados a museo)\n");
    	
    	// Situacion de partida:
    	// e0 desligado

		Assert.assertNotNull(empDao.recuperaPorDni(produtorDatos.e0.getDni()));
    	empDao.elimina(produtorDatos.e0);
		Assert.assertNull(empDao.recuperaPorDni(produtorDatos.e0.getDni()));
    } 	

    @Test
    public void t3b_CRUD_TestElimina() {

    	log.info("");
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

    	produtorDatos.creaMuseoconEmpleados();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaEmpleados();

    	log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de eliminacion de museo con empleados asociados\n");

    	// Situacion de partida:
    	// m1, e0, e1 desligados

    	Assert.assertNotNull(musDao.recuperaPorNombre(produtorDatos.m1.getNombre()));
		Assert.assertNotNull(empDao.recuperaPorDni(produtorDatos.e1.getDni()));

		// Aqui el remove sobre m1 debe propagarse a e0 y e1
		musDao.elimina(produtorDatos.m1);

		Assert.assertNull(musDao.recuperaPorNombre(produtorDatos.m1.getNombre()));
		Assert.assertNull(empDao.recuperaPorDni(produtorDatos.e1.getDni()));

    }

    @Test 
    public void t4_CRUD_TestModifica() {

    	Empleados e1, e1b;
		Museo m1, m1b;
    	int nuevoSueldo;
		Empleados nuevoEmpleado;
		Empleados e;
		Set<Empleados> listaEmpleados = new HashSet<Empleados>();
    	
    	log.info("");	
		log.info("Configurando situacion de partida do test -----------------------------------------------------------------------");
  
		produtorDatos.creaMuseoconEmpleados();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaEmpleados();

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Proba de modificacion da informacion dunha entrada de log solta\n");
 
    	
    	// Situacion de partida:
    	// e0 desligado
    	
		nuevoSueldo = 1200;

		e1 = (Empleados) empDao.recuperaPorDni(produtorDatos.e1.getDni());

		Assert.assertNotEquals(nuevoSueldo, e1.getSueldo());
    	e1.setSueldo(nuevoSueldo);

    	empDao.modifica(e1);
    	
		e1b = (Empleados) empDao.recuperaPorDni(produtorDatos.e1.getDni());
		Assert.assertEquals (nuevoSueldo, e1b.getSueldo());

		nuevoEmpleado = produtorDatos.e2;
		listaEmpleados.add(nuevoEmpleado);

		m1 = musDao.recuperaPorNombre(produtorDatos.m1.getNombre());
		m1 = musDao.restauraEmpleados(m1);

		Assert.assertNotEquals(listaEmpleados, m1.getEmpleados());
		m1.setEmpleados(listaEmpleados);

		musDao.modifica(m1);

		m1b = musDao.restauraEmpleados(m1);

		Assert.assertEquals (listaEmpleados, m1b.getEmpleados());
    } 	

    @Test
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		produtorDatos.crearMuseosSueltos();
		produtorDatos.grabaMuseos();
		produtorDatos.crearEmpleadosSueltos();

		produtorDatos.m1.agregarEmpleado(produtorDatos.e0);
		empDao.alta(produtorDatos.e0);
		
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de violacion de restricciones not null y unique\n"
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Gravacion de empleado con museo nulo\n"
    			+ "\t\t\t\t b) Gravacion de empleado con dni nulo\n"
    			+ "\t\t\t\t c) Gravacion de empleado con dni duplicado\n");

    	
		log.info("Probando gravacion de entrada con museo nulo ------------------------------------------------------------------");
    	try {
    		empDao.alta(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

    	// Ligar empleado a museo para poder probar otros errores
		produtorDatos.m1.agregarEmpleado(produtorDatos.e1);
    	    	
    	log.info("");	
		log.info("Probando gravacion de empleado con dni nulo -------------------------------------------------------------------");
		produtorDatos.e1.setDni(null);
    	try {
        	empDao.alta(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

    	log.info("");	
		log.info("Probando gravacion de empleado con dni duplicado --------------------------------------------------------------");
		produtorDatos.e1.setDni(produtorDatos.e0.getDni());
    	try {
        	empDao.alta(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    } 	

}