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
		log.info("Limpando BD --------------------------------------------------------------------------------------------");
		productorDatos.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void t1_CRUD_TestAlmacena() {

		log.info("");
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		productorDatos.crearMuseosSueltos();

		log.info("");
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
		log.info("Obxectivo: Proba de gravación na BD de novo usuario (sen entradas de log asociadas)\n");

		// Situación de partida:
		// u0 transitorio

		Assert.assertNull(productorDatos.m0.getIdmuseo());
		musDao.alta(productorDatos.m0);
		Assert.assertNotNull(productorDatos.m0.getIdmuseo());
	}

}