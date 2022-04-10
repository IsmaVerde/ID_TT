package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.SocioDao;
import gei.id.tutelado.dao.SocioDaoJPA;
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
    private static SocioDao socDao;
    
    @Rule
    public TestRule watcher = new TestWatcher() {
       protected void starting(Description description) {
    	   log.info("");
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	   log.info("Iniciando test SOCIOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS: " + description.getMethodName());
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
       }
       protected void finished(Description description) {
    	   log.info("");
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
    	   log.info("Finalizado test ISMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
				   "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
				   "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
				   "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
				   "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
				   "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
				   "SSSSSSSSSSSSSSSSSSSSSSSSSSS: " + description.getMethodName());
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
       }
    };
    
    @BeforeClass
    public static void init() throws Exception {
    	cfg = new ConfiguracionJPA();
    	cfg.start();

    	socDao = new SocioDaoJPA();
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
		log.info("Limpando BD --------------------------------------------------------------------------------------------");
		productoDatosPrueba.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}
	

    @Test 
    public void t1_CRUD_TestAlmacena() {

    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");
  
		productoDatosPrueba.crearSociosSueltos();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de gravación na BD de novo usuario (sen entradas de log asociadas)\n");
    	
    	// Situación de partida:
    	// s0 transitorio    	
    	
    	Assert.assertNull(productoDatosPrueba.s0.getId());
    	socDao.alta(productoDatosPrueba.s0);    	
    	Assert.assertNotNull(productoDatosPrueba.s0.getId());
    }
    
    @Test 
    public void t2_CRUD_TestRecupera() {
    	
    	Socios s;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	productoDatosPrueba.gravaSocios();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de recuperación desde a BD de usuario (sen entradas asociadas) por nif\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperación por nif existente\n"
    			+ "\t\t\t\t b) Recuperacion por nif inexistente\n");

    	// Situación de partida:
    	// s0 desligado    	

    	log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

    	s = (Socios) socDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
    	Assert.assertEquals(productoDatosPrueba.s0.getDni(),      s.getDni());
    	Assert.assertEquals(productoDatosPrueba.s0.getNombrecompleto(),     s.getNombrecompleto());
		Assert.assertEquals(productoDatosPrueba.s0.getEmail(),     s.getEmail());

    	log.info("");	
		log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");
    	
    	s = (Socios) socDao.recuperaPorDni("iwbvyhuebvuwebvi");
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
    	log.info("Obxectivo: Proba de eliminación da BD de usuario sen entradas asociadas\n");   
 
    	// Situación de partida:
    	// s0 desligado  

    	Assert.assertNotNull(socDao.recuperaPorDni(productoDatosPrueba.s0.getDni()));
    	socDao.elimina(productoDatosPrueba.s0);    	
    	Assert.assertNull(socDao.recuperaPorDni(productoDatosPrueba.s0.getDni()));
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
    	log.info("Obxectivo: Proba de modificación da información básica dun usuario sen entradas de log\n");

    	// Situación de partida:
    	// s0 desligado  

		novoNome = new String ("Nome novo");

		s1 = (Socios) socDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
		Assert.assertNotEquals(novoNome, s1.getNombrecompleto());
    	s1.setNombrecompleto(novoNome);

    	socDao.modifica(s1);    	
    	
		s2 = (Socios) socDao.recuperaPorDni(productoDatosPrueba.s0.getDni());
		Assert.assertEquals (novoNome, s2.getNombrecompleto());

    } 	
    
    
    @Test
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productoDatosPrueba.crearSociosSueltos();
    	socDao.alta(productoDatosPrueba.s0);
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de violación de restricións not null e unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Gravación de usuario con nif duplicado\n"
    			+ "\t\t\t\t b) Gravación de usuario con nif nulo\n");

    	// Situación de partida:
    	// s0 desligado, s1 transitorio
    	
		log.info("Probando gravacion de usuario con Nif duplicado -----------------------------------------------");
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
		log.info("Probando gravacion de usuario con Nif nulo ----------------------------------------------------");
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