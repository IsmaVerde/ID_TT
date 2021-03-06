package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.MuseoDao;
import gei.id.tutelado.dao.MuseoDaoJPA;
import gei.id.tutelado.model.Museo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1_Museo {

	private Logger log = LogManager.getLogger("gei.id.tutelado");

	private static DatosPrueba productorDatos = new DatosPrueba();

	private static Configuracion cfg;
	private static MuseoDao musDao;

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
		musDao.setup(cfg);

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
		log.info("Limpiando BD --------------------------------------------------------------------------------------------");
		productorDatos.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void t1_CRUD_TestAlta() {

		log.info("");
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();

		log.info("");
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
		log.info("Objetivo: Prueba de grabacion en la BD del nuevo museo (sin empleados ni socios)\n");

		// Situaci??n de partida:
		// m0 transitorio

		Assert.assertNull(productorDatos.m0.getIdmuseo());
		musDao.alta(productorDatos.m0);
		Assert.assertNotNull(productorDatos.m0.getIdmuseo());
	}

	@Test
	public void t2_CRUD_TestRecupera() {

		Museo m;

		log.info("");
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();
		productorDatos.grabaMuseos();

		log.info("");
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
		log.info("Objetivo: Prueba de recuperacion desde la BD de museo (sin socios ni empleados) por nombre\n"
				+ "\t\t\t\t Casos contemplados:\n"
				+ "\t\t\t\t a) Recuperacion por nombre existente\n"
				+ "\t\t\t\t b) Recuperacion por nombre inexistente\n");


		log.info("Probando recuperacion por nombre EXISTENTE --------------------------------------------------");

		m = musDao.recuperaPorNombre(productorDatos.m0.getNombre());
		Assert.assertEquals(productorDatos.m0.getNombre(),      m.getNombre());
		Assert.assertEquals(productorDatos.m0.getCategoria(),     m.getCategoria());
		Assert.assertEquals(productorDatos.m0.getUbicacion(), m.getUbicacion());

		log.info("");
		log.info("Probando recuperacion por nombre INEXISTENTE -----------------------------------------------");

		m = musDao.recuperaPorNombre("Monseo del Prado");
		Assert.assertNull (m);
	}

	@Test
	public void t3_CRUD_TestElimina() {

		log.info("");
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();
		productorDatos.grabaMuseos();

		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
		log.info("Objetivo: Prueba de eliminaci??n de la BD del museo sin inscritos ni empleados\n");

		// Situaci??n de partida:
		// m0 desligado

		Assert.assertNotNull(musDao.recuperaPorNombre(productorDatos.m0.getNombre()));
		musDao.elimina(productorDatos.m0);
		Assert.assertNull(musDao.recuperaPorNombre(productorDatos.m0.getNombre()));
	}

	@Test
	public void t4_CRUD_TestModifica() {

		Museo m1, m2;
		String nuevaCategoria;

		log.info("");
		log.info("Configurando situaci??n de partida del test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();
		productorDatos.grabaMuseos();

		log.info("");
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
		log.info("Objetivo: Prueba de modificaci??n de la informaci??n b??sica de un museo sin socios ni empleados\n");


		nuevaCategoria = new String ("Categoria nueva");

		m1 = musDao.recuperaPorNombre(productorDatos.m0.getNombre());
		Assert.assertNotEquals(nuevaCategoria, m1.getCategoria());
		m1.setCategoria(nuevaCategoria);

		musDao.modifica(m1);

		m2 = musDao.recuperaPorNombre(productorDatos.m0.getNombre());
		Assert.assertEquals (nuevaCategoria, m2.getCategoria());
	}

	@Test
	public void t5_CRUD_TestExcepcions() {

		Boolean excepcion;

		log.info("");
		log.info("Configurando situacion de partida del test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();
		musDao.alta(productorDatos.m0);

		log.info("");
		log.info("Inicio del test --------------------------------------------------------------------------------------------------");
		log.info("Objetivo: Prueba de violacion de restriciones not null y unique\n"
				+ "\t\t\t\t Casos contemplados:\n"
				+ "\t\t\t\t a) Grabaci??n de museo con nombre duplicado\n"
				+ "\t\t\t\t b) Grabaci??n de museo con nombre nulo\n");


		log.info("Probando grabacion de museo con nombre duplicado -----------------------------------------------");

		productorDatos.m1.setNombre(productorDatos.m0.getNombre());
		try {
			musDao.alta(productorDatos.m1);
			excepcion=false;
		} catch (Exception ex) {
			excepcion=true;
			log.info(ex.getClass().getName());
		}
		Assert.assertTrue(excepcion);

		// Nombre nulo
		log.info("");
		log.info("Probando grabacion de museo con Nombre nulo ----------------------------------------------------");
		productorDatos.m1.setNombre(null);
		try {
			musDao.alta(productorDatos.m1);
			excepcion=false;
		} catch (Exception ex) {
			excepcion=true;
			log.info(ex.getClass().getName());
		}
		Assert.assertTrue(excepcion);
	}
}