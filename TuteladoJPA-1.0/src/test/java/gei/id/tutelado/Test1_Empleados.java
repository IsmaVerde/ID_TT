package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.model.Empleados;
import gei.id.tutelado.dao.EmpleadoDao;
import gei.id.tutelado.dao.EmpleadoDaoJPA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1_Empleados {

	private Logger log = LogManager.getLogger("gei.id.tutelado");

	private static DatosPrueba productoDatosPrueba = new DatosPrueba();

	private static Configuracion cfg;
	private static EmpleadoDao empDao;

	@Rule
	public TestRule watcher = new TestWatcher() {
		protected void starting(Description description) {
			log.info("");
			log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			log.info("Iniciando test Empleados: " + description.getMethodName());
			log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}
		protected void finished(Description description) {
			log.info("");
			log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
			log.info("Finalizado test" + description.getMethodName());
			log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
		}
	};

	@BeforeClass
	public static void init() throws Exception {
		cfg = new ConfiguracionJPA();
		cfg.start();

		empDao = new EmpleadoDaoJPA();

		empDao.setup(cfg);

		productoDatosPrueba = new DatosPrueba();
		productoDatosPrueba.Setup(cfg);
	}

	@AfterClass
	public static void endclose() throws Exception {
		cfg.endUp();
	}


	@Before
	public void setUp() throws Exception {
		log.info("");
		log.info("Limpiando BD --------------------------------------------------------------------------------------------");
		productoDatosPrueba.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void t1_CRUD_TestAlta() {

		log.info("");
		log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearEmpleadosSueltos();

		log.info("");
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
		log.info("Objetivo: Prueba de grabación en la BD de nuevo empleado (sin museos asociados)\n");

		// Situación de partida:
		// e0 transitorio

		System.out.println(productoDatosPrueba.e0);
		empDao.alta(productoDatosPrueba.e0);
		Assert.assertNotNull(productoDatosPrueba.e0.getId());
	}
    
    @Test
    public void t2_CRUD_TestRecupera() {
    	
    	Empleados e;
    	
    	log.info("");	
		log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearEmpleadosSueltos();
    	productoDatosPrueba.grabaEmpleados();
    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Pruba de recuperación desde la BD de socio (sin museos asociado) por dni\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperación por dni existente\n"
    			+ "\t\t\t\t b) Recuperacion por dni inexistente\n");

    	// Situación de partida:
    	// e0 desligado    	

    	log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------");

    	e = (Empleados) empDao.recuperaPorDni(productoDatosPrueba.e0.getDni());
    	Assert.assertEquals(productoDatosPrueba.e0.getDni(),      e.getDni());
    	Assert.assertEquals(productoDatosPrueba.e0.getNombrecompleto(),     e.getNombrecompleto());
		Assert.assertEquals(productoDatosPrueba.e0.getEmail(),     e.getEmail());

    	log.info("");	
		log.info("Probando recuperacion por dni INEXISTENTE -----------------------------------------------");
    	
    	e = (Empleados) empDao.recuperaPorDni("DniInvalido");
    	Assert.assertNull (e);

    } 	

    @Test 
    public void t3_CRUD_TestElimina() {
    	
    	log.info("");	
		log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearEmpleadosSueltos();
    	productoDatosPrueba.grabaEmpleados();

    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de eliminación de la BD de socio sin museos asociados\n");   
 
    	// Situación de partida:
    	// e0 desligado  

    	Assert.assertNotNull(empDao.recuperaPorDni(productoDatosPrueba.e0.getDni()));
    	empDao.elimina(productoDatosPrueba.e0);    	
    	Assert.assertNull(empDao.recuperaPorDni(productoDatosPrueba.e0.getDni()));
    } 	

    @Test 
    public void t4_CRUD_TestModifica() {
    	
    	Empleados e1, e2;
    	String nuevoNombre;
    	
    	log.info("");	
		log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearEmpleadosSueltos();
    	productoDatosPrueba.grabaEmpleados();

    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de modificación de la información básica de un socio sin museos\n");

    	// Situación de partida:
    	// e0 desligado  

		nuevoNombre = new String ("Nombre nuevo");

		e1 = (Empleados) empDao.recuperaPorDni(productoDatosPrueba.e0.getDni());
		Assert.assertNotEquals(nuevoNombre, e1.getNombrecompleto());
    	e1.setNombrecompleto(nuevoNombre);

    	empDao.modifica(e1);    	
    	
		e2 = (Empleados) empDao.recuperaPorDni(productoDatosPrueba.e0.getDni());
		Assert.assertEquals (nuevoNombre, e2.getNombrecompleto());

    } 	
    
    
    @Test
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearEmpleadosSueltos();
    	empDao.alta(productoDatosPrueba.e0);
    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de violación de restricións not null y unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Grabación de socio con dni duplicado\n"
    			+ "\t\t\t\t b) Grabación de socio con dni nulo\n");

    	// Situación de partida:
    	// e0 desligado, e1 transitorio
    	
		log.info("Probando grabación de socio con Nif duplicado -----------------------------------------------");
    	productoDatosPrueba.e1.setDni(productoDatosPrueba.e0.getDni());
    	try {
        	empDao.alta(productoDatosPrueba.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    	
    	// Nif nulo
    	log.info("");	
		log.info("Probando grabación de socio con Nif nulo ----------------------------------------------------");
    	productoDatosPrueba.e1.setDni(null);
    	try {
        	empDao.alta(productoDatosPrueba.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    }
    
}