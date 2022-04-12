package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.MuseoDao;
import gei.id.tutelado.dao.MuseoDaoJPA;
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
		log.info("Limpando BD --------------------------------------------------------------------------------------------");
		productoDatosPrueba.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}
	

    @Test 
    public void t1_CRUD_TestAlta() {

    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");
  
		productoDatosPrueba.crearSociosSueltos();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de gravación na BD de novo empleado (sen entradas de log asociadas)\n");
    	
    	// Situación de partida:
    	// s0 transitorio

		System.out.println(productoDatosPrueba.e0);
		empDao.alta(productoDatosPrueba.e0);
    	Assert.assertNotNull(productoDatosPrueba.e0.getId());
    }
    
/*    @Test
    public void t2_CRUD_TestRecupera() {
    	
    	Socios s;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.gravaSocios();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de recuperación desde a BD de empleado (sen entradas asociadas) por nif\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperación por nif existente\n"
    			+ "\t\t\t\t b) Recuperacion por nif inexistente\n");

    	// Situación de partida:
    	// s0 desligado    	

    	log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

    	s = (Socios) empDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
    	Assert.assertEquals(productoDatosPrueba.s0.getDni(),      s.getDni());
    	Assert.assertEquals(productoDatosPrueba.s0.getNombrecompleto(),     s.getNombrecompleto());
		Assert.assertEquals(productoDatosPrueba.s0.getEmail(),     s.getEmail());

    	log.info("");	
		log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");
    	
    	s = (Socios) empDao.recuperaPorDni("iwbvyhuebvuwebvi");
    	Assert.assertNull (s);

    } 	

    @Test 
    public void t3_CRUD_TestElimina() {
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.gravaSocios();

    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de eliminación da BD de empleado sen entradas asociadas\n");   
 
    	// Situación de partida:
    	// s0 desligado  

    	Assert.assertNotNull(empDao.recuperaPorDni(productoDatosPrueba.s0.getDni()));
    	empDao.elimina(productoDatosPrueba.s0);    	
    	Assert.assertNull(empDao.recuperaPorDni(productoDatosPrueba.s0.getDni()));
    } 	

    @Test 
    public void t4_CRUD_TestModifica() {
    	
    	Socios s1, s2;
    	String novoNome;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.gravaSocios();

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de modificación da información básica dun empleado sen entradas de log\n");

    	// Situación de partida:
    	// s0 desligado  

		novoNome = new String ("Nome novo");

		s1 = (Socios) empDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
		Assert.assertNotEquals(novoNome, s1.getNombrecompleto());
    	s1.setNombrecompleto(novoNome);

    	empDao.modifica(s1);    	
    	
		s2 = (Socios) empDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
		Assert.assertEquals (novoNome, s2.getNombrecompleto());

    } 	
    
    
    @Test
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	empDao.alta(productoDatosPrueba.s0);
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de violación de restricións not null e unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Gravación de empleado con nif duplicado\n"
    			+ "\t\t\t\t b) Gravación de empleado con nif nulo\n");

    	// Situación de partida:
    	// s0 desligado, s1 transitorio
    	
		log.info("Probando gravacion de empleado con Nif duplicado -----------------------------------------------");
    	productoDatosPrueba.s1.setDni(productoDatosPrueba.s0.getDni());
    	try {
        	empDao.alta(productoDatosPrueba.s1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    	
    	// Nif nulo
    	log.info("");	
		log.info("Probando gravacion de empleado con Nif nulo ----------------------------------------------------");
    	productoDatosPrueba.s1.setDni(null);
    	try {
        	empDao.alta(productoDatosPrueba.s1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    } 	*/
    
}