package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.PersonaDao;
import gei.id.tutelado.dao.PersonaDaoJPA;
import gei.id.tutelado.model.Socios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1_Socios {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static DatosPrueba productoDatosPrueba = new DatosPrueba();
    
    private static Configuracion cfg;
    private static PersonaDao socDao;
    
    @Rule
    public TestRule watcher = new TestWatcher() {
       protected void starting(Description description) {
    	   log.info("");
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	   log.info("Iniciando test Socios: " + description.getMethodName());
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
       }
       protected void finished(Description description) {
    	   log.info("");
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
    	   log.info("Finalizado test " + description.getMethodName());
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
       }
    };
    
    @BeforeClass
    public static void init() throws Exception {
    	cfg = new ConfiguracionJPA();
    	cfg.start();

    	socDao = new PersonaDaoJPA();
    	socDao.setup(cfg);
    	
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
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");
  
		productoDatosPrueba.crearSociosSueltos();
    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de grabacion en la BD del nuevo socio (sin museos asociados)\n");
    	
    	// Situacion de partida:
    	// s0 transitorio

		Assert.assertNull(productoDatosPrueba.s2.getId());
		socDao.alta(productoDatosPrueba.s2);
    	Assert.assertNotNull(productoDatosPrueba.s2.getId());
    }
    
    @Test
    public void t2_CRUD_TestRecupera() {
    	
    	Socios s;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.grabaSocios();
    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de recuperacion desde la BD de socio (sin museos asociado) por dni\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperacion por dni existente\n"
    			+ "\t\t\t\t b) Recuperacion por dni inexistente\n");

    	// Situacion de partida:
    	// s0 desligado    	

    	log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------");

    	s = (Socios) socDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
    	Assert.assertEquals(productoDatosPrueba.s0.getDni(),      s.getDni());
    	Assert.assertEquals(productoDatosPrueba.s0.getNombrecompleto(),     s.getNombrecompleto());
		Assert.assertEquals(productoDatosPrueba.s0.getEmail(),     s.getEmail());

    	log.info("");	
		log.info("Probando recuperacion por dni INEXISTENTE -----------------------------------------------");
    	
    	s = (Socios) socDao.recuperaPorDni("DniInvalido");
    	Assert.assertNull (s);
    } 	

    @Test 
    public void t3_CRUD_TestElimina() {
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.grabaSocios();

    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de eliminacion de la BD de socio sin museos asociados\n");   
 
    	// Situacion de partida:
    	// s0 desligado  

    	Assert.assertNotNull(socDao.recuperaPorDni(productoDatosPrueba.s0.getDni()));
    	socDao.elimina(productoDatosPrueba.s0);    	
    	Assert.assertNull(socDao.recuperaPorDni(productoDatosPrueba.s0.getDni()));
    } 	

    @Test 
    public void t4_CRUD_TestModifica() {
    	
    	Socios s1, s2;
    	String nuevoNombre;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.grabaSocios();

    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de modificacion de la informacion basica de un socio sin museos\n");

    	// Situacion de partida:
    	// s0 desligado  

		nuevoNombre = new String ("Nombre nuevo");

		s1 = (Socios) socDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
		Assert.assertNotEquals(nuevoNombre, s1.getNombrecompleto());
    	s1.setNombrecompleto(nuevoNombre);

    	socDao.modifica(s1);    	
    	
		s2 = (Socios) socDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
		Assert.assertEquals (nuevoNombre, s2.getNombrecompleto());

    } 	
    
    
    @Test
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	socDao.alta(productoDatosPrueba.s0);
    	
    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de violacion de restricions not null y unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Grabacion de socio con dni duplicado\n"
    			+ "\t\t\t\t b) Grabacion de socio con dni nulo\n");

    	// Situacion de partida:
    	// s0 desligado, s1 transitorio
    	
		log.info("Probando grabacion de socio con Nif duplicado -----------------------------------------------");
    	productoDatosPrueba.s1.setDni(productoDatosPrueba.s0.getDni());
    	try {
        	socDao.alta(productoDatosPrueba.s1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    	
    	// Nif nulo
    	log.info("");	
		log.info("Probando grabacion de socio con Nif nulo ----------------------------------------------------");
    	productoDatosPrueba.s1.setDni(null);
    	try {
        	socDao.alta(productoDatosPrueba.s1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    }
    
}