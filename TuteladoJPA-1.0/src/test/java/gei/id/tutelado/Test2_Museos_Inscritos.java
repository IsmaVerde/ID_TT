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
		log.info("Limpiando BD -----------------------------------------------------------------------------------------------------");
		produtorDatos.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}	

	
    @Test //Test alta de inscritos a un museo
    public void t1_CRUD_Testalta() {


    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		produtorDatos.crearSociosSueltos();
		produtorDatos.crearMuseosSueltos();
    	produtorDatos.grabaMuseos();


    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba da grabacion de socio sueltos\n"
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Primer socio inscrito a un museo\n"
    			+ "\t\t\t\t b) Nuevo socio para un museo con inscritos\n");

    	log.info("");	
		log.info("Grabando socio de un museo --------------------------------------------------------------------");
    	Assert.assertNull(produtorDatos.s0.getId());
    	socDao.alta(produtorDatos.s0);
    	Assert.assertNotNull(produtorDatos.s0.getId());
		Assert.assertTrue(produtorDatos.m1.getInscritos().isEmpty());
		produtorDatos.m1.agregarSocios(produtorDatos.s0);
		Assert.assertEquals(produtorDatos.m1.getInscritos().size(),1);
		Assert.assertTrue(produtorDatos.m1.getInscritos().contains(produtorDatos.s0));


    	log.info("");	
		log.info("Grabando segundo socio de un museo ---------------------------------------------------------------------");
    	socDao.alta(produtorDatos.s1);
		produtorDatos.m1.agregarSocios(produtorDatos.s1);
    	Assert.assertEquals(produtorDatos.m1.getInscritos().size(),2);
		Assert.assertTrue(produtorDatos.m1.getInscritos().contains(produtorDatos.s1));
    }

    @Test
    public void t2a_CRUD_TestRecupera() {
   	
    	Socios s;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		produtorDatos.creaMuseoconInscritos();
		produtorDatos.grabaMuseos();
    	produtorDatos.grabaSocios();


		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de la recuperacion (por dni) de socio inscrito a museo\n"
		+ "\t\t\t\t Casos contemplados:\n"
		+ "\t\t\t\t a) Recuperacion por dni existente\n"
		+ "\t\t\t\t b) Recuperacion por dni inexistente\n");
    	
		log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------");

    	s = (Socios) socDao.recuperaPorDni(produtorDatos.s0.getDni());

    	Assert.assertEquals (produtorDatos.s0.getDni(),    s.getDni());
    	Assert.assertEquals (produtorDatos.s0.getEmail(), s.getEmail());
    	Assert.assertEquals (produtorDatos.s0.getNombrecompleto(),   s.getNombrecompleto());

    	log.info("");	
		log.info("Probando recuperacion por dni INEXISTENTE --------------------------------------------------");
    	
    	s = (Socios) socDao.recuperaPorDni("iwbvyhuebvuwebvi");
    	Assert.assertNull (s);
    } 	

    @Test
    public void t2b_CRUD_TestRecupera() {
    	
    	Museo m;
    	Socios s;
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		produtorDatos.creaMuseoconInscritos();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaSocios();

		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de la recuperacin de propiedades LAZY\n"   
		+ "\t\t\t\t Casos contemplados:\n"
		+ "\t\t\t\t a) Recuperacion de museo con coleccion (LAZY) de socios \n"
		+ "\t\t\t\t b) Carga forzada de coleccion LAZY de dicha coleccion de socios\n"
		+ "\t\t\t\t c) Recuperacion de entrada de socios suelta con referencia (EAGER) a museo\n");
    	
		log.info("Probando (excepcion tras) recuperacion LAZY ---------------------------------------------------------------------");
    	
    	m = musDao.recuperaPorNombre(produtorDatos.m1.getNombre());

		log.info("Acceso a los socios de un museo");
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

    @Test //eliminar museo
    public void t3a_CRUD_TestElimina() {
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

    	produtorDatos.creaMuseoconInscritos();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaSocios();

    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de eliminacion de entrada de socio inscrito a un museo\n");

		Assert.assertNotNull(socDao.recuperaPorDni(produtorDatos.s0.getDni()));
    	socDao.elimina(produtorDatos.s0);    	
		Assert.assertNull(socDao.recuperaPorDni(produtorDatos.s0.getDni()));

    } 	

    @Test
    public void t4_CRUD_TestModifica() {

    	Socios s1, s1b, s2,s2b;
    	String nuevaProfesion;
		Museo nuevoMuseo;
		Set<Museo> listaMuseos= new HashSet<Museo>();
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");
  
		produtorDatos.creaMuseoconInscritos();
    	produtorDatos.grabaMuseos();
		produtorDatos.grabaSocios();

    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objetivo: Prueba de modificacion de la informacion de socio inscrito a un museo\n");

		log.info("Modifica profesion\n");
		nuevaProfesion = new String ("Arquitecto");

		s1 = (Socios) socDao.recuperaPorDni(produtorDatos.s1.getDni());

		Assert.assertNotEquals(nuevaProfesion, s1.getProfesion());
    	s1.setProfesion(nuevaProfesion);

    	socDao.modifica(s1);
    	
		s1b = (Socios) socDao.recuperaPorDni(produtorDatos.s1.getDni());
		Assert.assertEquals (nuevaProfesion, s1b.getProfesion());

		log.info("Modifica lista de museos del socio inscrito\n");
		nuevoMuseo = produtorDatos.m1;
		listaMuseos.add(nuevoMuseo);

		s2 = (Socios) socDao.recuperaPorDni(produtorDatos.s2.getDni());

		Assert.assertFalse(s2.getMuseos().contains(nuevoMuseo));

		s2.setMuseos(listaMuseos);

		socDao.modifica(s2);

		s2b = (Socios) socDao.recuperaPorDni(produtorDatos.s2.getDni());
		Assert.assertEquals (listaMuseos, s2b.getMuseos());

    } 	

    @Test //Codigos nulos o duplicados
    public void t5_CRUD_TestExcepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		produtorDatos.crearMuseosSueltos();
		produtorDatos.crearSociosSueltos();
		produtorDatos.grabaMuseos();

		socDao.alta(produtorDatos.s0);
		produtorDatos.m1.agregarSocios(produtorDatos.s0);		

    	log.info("");	
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
    	log.info("Objectivo: Prueba de violacion de restricciones not null y unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Grabacion de entrada con museo nulo\n"
    			+ "\t\t\t\t b) Grabacion de entrada con dni nulo\n"
    			+ "\t\t\t\t c) Grabacion de entrada con dni duplicado\n");

    	// Ligar entrada a museo para poder probar outros erros
		produtorDatos.m1.agregarSocios(produtorDatos.s1);
    	    	
    	log.info("");	
		log.info("Probando grabacion con dni nulo -------------------------------------------------------------------");
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
		log.info("Probando grabacion de entrada con dni duplicado --------------------------------------------------------------");
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