package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test3_Consultas_TT {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static DatosPrueba productorDatos = new DatosPrueba();
    
    private static Configuracion cfg;
    private static MuseoDao musDao;
    private static PersonaDao socDao;
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
    	socDao = new PersonaDaoJPA();
		empDao = new PersonaDaoJPA();
    	musDao.setup(cfg);
    	socDao.setup(cfg);
		empDao.setup(cfg);
    	
    	productorDatos = new DatosPrueba();
    	productorDatos.Setup(cfg);
    }
    
    @AfterClass
    public static void endclose() throws Exception {
    	cfg.endUp();    	
    }


	@Before
	public void setUp() throws Exception {		
		log.info("");	
		log.info("Limpando BD -----------------------------------------------------------------------------------------------------");
		productorDatos.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}	

	
    @Test 
    public void t1_CRUD_TestQuery_Museo_recuperaporNombre() {

    	Museo listaM;

    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();
		productorDatos.grabaMuseos();

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba da consulta Usuario.recuperaTodos\n");   

    	// Situación de partida:
    	// u1, e1A, e1B desligados

		listaM = musDao.recuperaPorNombre("Museo Domus");

		Assert.assertEquals(listaM.getNombre(),"Museo Domus");
		Assert.assertEquals(listaM.getUbicacion(),"A Corunha");
		Assert.assertEquals(listaM.getCategoria(),"Ciencias");
    }

	@Test
	public void t1_CRUD_TestQuery_Museo_recuperaSociosSinMuseo() {

		List<Socios> listaS;
		Socios soc0, soc6;

		log.info("");
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productorDatos.crearSociosSueltos();
		productorDatos.creaMuseoconInscritos();
		productorDatos.grabaMuseos();
		productorDatos.grabaSocios();

		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
		log.info("Obxectivo: Proba da consulta Usuario.recuperaSociosSinMuseo\n");

		// Situación de partida:
		// u1, e1A, e1B desligados

		listaS = musDao.recuperaSociosSinMuseo();
		soc0= listaS.get(0);
		soc6= listaS.get(8);

		Assert.assertEquals("0000",soc0.getDni());
		Assert.assertEquals("Primer Socio",soc0.getNombrecompleto());
		Assert.assertEquals("Francia",soc0.getProcedencia());
		Assert.assertNotEquals(0,soc0.getMuseos().size());

		Assert.assertEquals("5555",soc6.getDni());
		Assert.assertEquals("Sexto Socio",soc6.getNombrecompleto());
		Assert.assertEquals("Alemania",soc6.getProcedencia());
		Assert.assertEquals(0,soc6.getMuseos().size());
	}

	@Test
	public void t1_CRUD_TestQuery_Persona_recuperaPorDni(){

		Socios Socio;
		Empleados Empleado;

		log.info("");
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productorDatos.crearEmpleadosSueltos();
		productorDatos.crearSociosSueltos();
		productorDatos.grabaSocios();


		productorDatos.creaMuseoCompleto();

		productorDatos.grabaMuseos();
		productorDatos.grabaEmpleados();

		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
		log.info("Obxectivo: Proba da consulta Usuario.recuperaTodos\n");

		// Situación de partida:
		// u1, e1A, e1B desligados

		Socio = (Socios) socDao.recuperaPorDni("3333");
		
		Assert.assertEquals(Socio.getDni(),"3333");
		Assert.assertEquals(Socio.getNombrecompleto(),"Cuarto Socio");
		Assert.assertEquals(Socio.getEmail(),"Cuarto@hotmail");
		Assert.assertEquals(Socio.getProfesion(),"Juez");
		Assert.assertEquals(Socio.getProcedencia(),"España");

		Empleado = (Empleados) empDao.recuperaPorDni("0004");

		Assert.assertEquals(Empleado.getDni(),"0004");
		Assert.assertEquals(Empleado.getNombrecompleto(),"Cuarto Empleado");
		Assert.assertEquals(Empleado.getEmail(),"Cuartempleadoo@hotmail");
		Assert.assertEquals(Empleado.getPuesto(),"Restaurador");
		Assert.assertNull(Empleado.getExperiencia());

	}

	@Test
	public void t1_CRUD_TestQuery_Museo_recuperaSueldoMedio(){

		List<Double> listaE;
		double mediasalario =0;
		int tmp = 0;



		log.info("");
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productorDatos.crearEmpleadosSueltos();
		productorDatos.creaMuseoconEmpleados();
		productorDatos.grabaMuseos();
		productorDatos.grabaEmpleados();

		List<Empleados> empleados = Arrays.asList(productorDatos.e0, productorDatos.e1, productorDatos.e2,
				productorDatos.e3, productorDatos.e4);

		for(int i=0;i < empleados.size(); i++){
			tmp+=empleados.get(i).getSueldo();
		}
		mediasalario= tmp/empleados.size();


		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
		log.info("Obxectivo: Proba da consulta Usuario.recuperaTodos\n");

		// Situación de partida:
		// u1, e1A, e1B desligados

		listaE = socDao.recuperaSueldoMedio();
		Assert.assertEquals(listaE.toArray()[0],mediasalario);
	}

	@Test
	public void t1_CRUD_TestQuery_Museo_recuperaSociosMinDosMuseos(){

		List<Socios> listaS;
		Socios soc0, soc1, soc2;

		log.info("");
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productorDatos.crearSociosSueltos();
		productorDatos.creaMuseoconInscritos();
		productorDatos.grabaMuseos();
		productorDatos.grabaSocios();

		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
		log.info("Obxectivo: Proba da consulta Usuario.recuperaTodos\n");

		// Situación de partida:
		// u1, e1A, e1B desligados

		listaS = socDao.recuperaSociosMinDosMuseos();
		soc0 = listaS.get(0);
		soc1 = listaS.get(1);
		soc2 =listaS.get(2);

		Assert.assertEquals("0000",soc0.getDni());
		Assert.assertEquals("Primer Socio",soc0.getNombrecompleto());
		Assert.assertEquals("Francia",soc0.getProcedencia());

		Assert.assertEquals("1111",soc1.getDni());
		Assert.assertEquals("Segundo Socio",soc1.getNombrecompleto());
		Assert.assertEquals("España",soc1.getProcedencia());

		Assert.assertEquals("4444",soc2.getDni());
		Assert.assertEquals("Quinto Socio",soc2.getNombrecompleto());
		Assert.assertEquals("Italia",soc2.getProcedencia());
	}
}