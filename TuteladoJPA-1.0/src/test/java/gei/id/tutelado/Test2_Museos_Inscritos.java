package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.PersonaDao;
import gei.id.tutelado.dao.PersonaDaoJPA;
import gei.id.tutelado.dao.MuseoDao;
import gei.id.tutelado.dao.MuseoDaoJPA;
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
public class Test2_Museos_Inscritos {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static DatosPrueba produtorDatos = new DatosPrueba();
    
    private static Configuracion cfg;
    private static MuseoDao musDao;
    private static PersonaDao socDao;
    
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
    	socDao = new PersonaDaoJPA();
    	musDao.setup(cfg);
    	socDao.setup(cfg);
    	
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

	
    @Test //Test alta de inscritos a un museo
    public void t1_CRUD_Testalta() {


    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.crearSociosSueltos();
		produtorDatos.crearMuseosSueltos();
    	produtorDatos.grabaMuseos();


    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba da gravación de entradas de log soltas\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Primeira entrada de log vinculada a un usuario\n"
    			+ "\t\t\t\t b) Nova entrada de log para un usuario con entradas previas\n");     	

    	// Situación de partida:
    	// m1 desligado    	
    	// s0, s1 transitorios


    	log.info("");	
		log.info("Gravando primeira entrada de log dun usuario --------------------------------------------------------------------");
    	Assert.assertNull(produtorDatos.s0.getId());
    	socDao.alta(produtorDatos.s0);
    	Assert.assertNotNull(produtorDatos.s0.getId());
		Assert.assertTrue(produtorDatos.m1.getInscritos().isEmpty());
		produtorDatos.m1.agregarSocios(produtorDatos.s0);
		Assert.assertEquals(produtorDatos.m1.getInscritos().size(),1);
		Assert.assertTrue(produtorDatos.m1.getInscritos().contains(produtorDatos.s0));


    	log.info("");	
		log.info("Gravando segunda entrada de log dun usuario ---------------------------------------------------------------------");
    	socDao.alta(produtorDatos.s1);
		produtorDatos.m1.agregarSocios(produtorDatos.s1);
    	Assert.assertEquals(produtorDatos.m1.getInscritos().size(),2);
		Assert.assertTrue(produtorDatos.m1.getInscritos().contains(produtorDatos.s1));
    }

    @Test //Test recupera por dni sin lazy ni eager
    public void t2a_CRUD_TestRecupera() {
   	
    	Socios s;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaMuseoconInscritos();
		produtorDatos.grabaMuseos();
    	produtorDatos.grabaSocios();


		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba da recuperación (por codigo) de entradas de log soltas\n"   
		+ "\t\t\t\t Casos contemplados:\n"
		+ "\t\t\t\t a) Recuperación por codigo existente\n"
		+ "\t\t\t\t b) Recuperacion por codigo inexistente\n");     	

    	// Situación de partida:
    	// m1, s0, s1 desligados
    	
		log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------");

    	s = (Socios) socDao.recuperaPorDni(produtorDatos.s0.getDni());

    	Assert.assertEquals (produtorDatos.s0.getDni(),    s.getDni());
    	Assert.assertEquals (produtorDatos.s0.getEmail(), s.getEmail());
    	Assert.assertEquals (produtorDatos.s0.getNombrecompleto(),   s.getNombrecompleto());

    	log.info("");	
		log.info("Probando recuperacion por codigo INEXISTENTE --------------------------------------------------");
    	
    	s = (Socios) socDao.recuperaPorDni("iwbvyhuebvuwebvi");
    	Assert.assertNull (s);
    } 	

    @Test //test Lazy por parte Museo e EAGER por parte Socio REVISAR//////////////////////////////////////////
    public void t2b_CRUD_TestRecupera() {
    	
    	Museo m;
    	Socios s;
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaMuseoconInscritos();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaSocios();

		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba da recuperación de propiedades LAZY\n"   
		+ "\t\t\t\t Casos contemplados:\n"
		+ "\t\t\t\t a) Recuperación de museo con colección (LAZY) de entradas de log \n"
		+ "\t\t\t\t b) Carga forzada de colección LAZY da dita coleccion\n"     	
		+ "\t\t\t\t c) Recuperacion de entrada de log solta con referencia (EAGER) a usuario\n");     	

    	// Situación de partida:
    	// m1, s0, s1 desligados
    	
		log.info("Probando (excepcion tras) recuperacion LAZY ---------------------------------------------------------------------");
    	
    	m = musDao.recuperaPorNombre(produtorDatos.m1.getNombre());
		Socios lastElement= new Socios();

		log.info("Acceso a entradas de log de usuario");
    	try	{
			Assert.assertEquals(2, m.getInscritos().size());
			Assert.assertTrue( m.getInscritos().contains(produtorDatos.s4));
			Assert.assertTrue( m.getInscritos().contains(produtorDatos.s1));
        	excepcion=false;
    	} catch (LazyInitializationException ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	};    	
    	Assert.assertTrue(excepcion);
    
    	log.info("");
    	log.info("Probando carga forzada de coleccion LAZY ------------------------------------------------------------------------");
    	
    	m = musDao.recuperaPorNombre((produtorDatos.m1.getNombre()));
    	m = musDao.restauraSocios(m);						// Usuario u con proxy xa inicializado
    	
    	Assert.assertEquals(2, m.getInscritos().size());
		System.out.println(m.getInscritos());
    	Assert.assertTrue( m.getInscritos().contains(produtorDatos.s4));
		Assert.assertTrue( m.getInscritos().contains(produtorDatos.s1));


		log.info("");
    	log.info("Probando acceso a referencia EAGER ------------------------------------------------------------------------------");
    
    	s = (Socios) socDao.recuperaPorDni(produtorDatos.s1.getDni());
    	Assert.assertTrue(s.getMuseos().contains(produtorDatos.m1));
    } 	

    @Test //eliminar usuario
    public void t3a_CRUD_TestElimina() {
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

    	produtorDatos.creaMuseoconInscritos();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaSocios();

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de eliminación de entrada de log solta (asignada a usuario)\n");
    	
    	// Situación de partida:
    	// s0 desligado

		Assert.assertNotNull(socDao.recuperaPorDni(produtorDatos.s0.getDni()));
    	socDao.elimina(produtorDatos.s0);    	
		Assert.assertNull(socDao.recuperaPorDni(produtorDatos.s0.getDni()));

    } 	

    @Test //Cambio profesión socios y cambio nombre museo asociados a ellos
    public void t4_CRUD_TestModifica() {

    	Socios s1, s1b, s2,s2b;
    	String nuevaProfesion;
		Museo nuevoMuseo;
		Set<Museo> listaMuseos= new HashSet<Museo>();
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");
  
		produtorDatos.creaMuseoconInscritos();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaSocios();

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de modificación da información dunha entrada de log solta\n");
 
    	
    	// Situación de partida:
    	// s0 desligado
    	
		nuevaProfesion = new String ("Arquitecto");

		s1 = (Socios) socDao.recuperaPorDni(produtorDatos.s1.getDni());

		Assert.assertNotEquals(nuevaProfesion, s1.getProfesion());
    	s1.setProfesion(nuevaProfesion);

    	socDao.modifica(s1);
    	
		s1b = (Socios) socDao.recuperaPorDni(produtorDatos.s1.getDni());
		Assert.assertEquals (nuevaProfesion, s1b.getProfesion());

    	// NOTA: Non probamos modificación de usuario da entrada porque non ten sentido no dominio considerado

		nuevoMuseo = produtorDatos.m1;
		listaMuseos.add(nuevoMuseo);

		s2 = (Socios) socDao.recuperaPorDni(produtorDatos.s2.getDni());

		Assert.assertFalse(s2.getMuseos().contains(nuevoMuseo));

		s2.setMuseos(listaMuseos);

		socDao.modifica(s2);

		s2b = (Socios) socDao.recuperaPorDni(produtorDatos.s2.getDni());
		Assert.assertEquals (listaMuseos, s2b.getMuseos());

    } 	

    @Test //Códigos nulos o duplicados
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.crearMuseosSueltos();
		produtorDatos.crearSociosSueltos();
		produtorDatos.grabaMuseos();

		socDao.alta(produtorDatos.s0);
		produtorDatos.m1.agregarSocios(produtorDatos.s0);		

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de violacion de restricions not null e unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Gravación de entrada con usuario nulo\n"
    			+ "\t\t\t\t b) Gravación de entrada con codigo nulo\n"
    			+ "\t\t\t\t c) Gravación de entrada con codigo duplicado\n");

    	// Ligar entrada a usuario para poder probar outros erros
		produtorDatos.m1.agregarSocios(produtorDatos.s1);
    	    	
    	log.info("");	
		log.info("Probando gravacion de entrada con codigo nulo -------------------------------------------------------------------");
		produtorDatos.s1.setDni(null);
    	try {
        	socDao.alta(produtorDatos.s1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

    	log.info("");	
		log.info("Probando gravacion de entrada con codigo duplicado --------------------------------------------------------------");
		produtorDatos.s1.setDni(produtorDatos.s0.getDni());
    	try {
        	socDao.alta(produtorDatos.s1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

    } 	

}