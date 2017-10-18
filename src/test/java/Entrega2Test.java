import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import OperacionesMatematicas.Operador;
import domain.DomainExceptions.ArchivoInvalidoException;
import domain.DomainExceptions.IndicadorInvalidoException;
import empresas.Empresa;
import expresiones.Expresion;
import expresiones.ExpresionCompuesta;
import expresiones.ExpresionNoNumerica;
import expresiones.ExpresionNumero;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import model.Archivo;
import model.ConversorFormatoArchivo;
import model.Cuenta;
import model.IConversorFormatoArchivo;
import model.Indicador;
import parser.Parser;
import repositorios.RepositorioIndicadores;

public class Entrega2Test extends AbstractPersistenceTest implements WithGlobalEntityManager {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoEmpresas manejador;
//    InviertiendoViewModel inviertiendoViewModel;
//    CuentaViewModel cuentasViewModel;
//    EmpresaViewModel empresaViewModel;
//    AgregarEmpresaViewModel agregarEmpresaViewModel;
    File file;
    Parser parser;
    RepositorioIndicadores repo;
	
	@Before
	public void initObjects() throws IOException{ 
		System.out.println("begin'");
		file = new File("cuentasMock.txt");
		file.createNewFile();
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoEmpresas("cuentasMock.txt");
//        inviertiendoViewModel = new InviertiendoViewModel();
//        cuentasViewModel = new CuentaViewModel();
//        empresaViewModel = new EmpresaViewModel();
        Archivo archivo = new Archivo();
        repo = new RepositorioIndicadores();
        parser = new Parser(repo);
        archivo.setRuta("cuentasMock.txt");
//        agregarEmpresaViewModel = new AgregarEmpresaViewModel();
//        agregarEmpresaViewModel.setRepoEmpresas(new RepositorioEmpresas(archivo.getRuta()));
//        cuentasViewModel.setArchivo(archivo);
//        empresaViewModel.setArchivo(archivo);
     
	}
	
	/* ***************************************** TESTS ENTREGA 2 & ENTREGA 3 ********************************************** */
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
//	@Test 
//	public void testPersistence(){
//		
//		EntityManager manager = PerThreadEntityManagers.getEntityManager();
//		EntityTransaction tx = manager.getTransaction();
//		
//		tx.begin();
//		
//		Cuenta unaCuenta = new Cuenta("Pepe", "202", "33");
//		manager.persist(una);
//		
//		tx.commit();
//	}
	
	@Test
	public void parsearFormulaSoloNumeros(){
		Expresion exp = parser.obtenerExpresion("10 * 10 + 2 / 2");
		Assert.assertEquals(51, exp.calcular(null, null), 0);
	}
	
	@Test
	public void parsearFormulaConCuentas(){
		Expresion exp = parser.obtenerExpresion("ROE * 5 + 100");
		Empresa unaEmpresa = new Empresa("test");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("ROE", "1454", "20"));
		
		Assert.assertEquals(200, exp.calcular(unaEmpresa, "1454"), 0);
	}
	
	@Test
	public void expresionCuentaCalculaCorrectamente(){
		
		Empresa unaEmpresa = new Empresa("test");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("patrimonio", "2017", "100"));
		
		Expresion expresion = new ExpresionNoNumerica("patrimonio", repo);
		Assert.assertEquals(100, expresion.calcular(unaEmpresa, "2017"), 0);
	}

	@Test (expected = IndicadorInvalidoException.class)
	public void expresionCuentaFallaPorFaltaDeDatos(){
		
		Empresa unaEmpresa = new Empresa("test");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("patrimonio", "2017", "100"));
		
		Expresion expresion = new ExpresionNoNumerica("fondos", repo);
		Assert.assertEquals(100, expresion.calcular(unaEmpresa, "2017"), 0);
	}
	
	@Test
	public void expresionCompuestaCalculaCorrectamente(){
		
		// C�lculo: 10 * 100 = 1000.
		
		Empresa unaEmpresa = new Empresa("Domingo");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("USS", "2000", "100"));
		
		Expresion expresion1 = new ExpresionNumero(10);
		Operador operador = Operador.MULTIPLICACION;
		Expresion expresion2 = new ExpresionNoNumerica("USS", repo);
		Expresion expresionCompuesta = new ExpresionCompuesta(expresion1, operador, expresion2);
		
		Assert.assertEquals(1000, expresionCompuesta.calcular(unaEmpresa, "2000"), 0);
	}
	
	@Test 
	public void expresionesCompuestasConExpresionesCompuestas(){
		
		// C�lculo: 10 * 100 + 500 - 3 = 1497.
		
		Empresa unaEmpresa = new Empresa("Domingo");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("USS", "1988", "100"));
		
		Operador operadorMul = Operador.MULTIPLICACION;
		Operador operadorSum = Operador.SUMA;
		Operador operadorRes = Operador.RESTA;
		Expresion expresion_10 = new ExpresionNumero(10);
		Expresion expresion_1000 = new ExpresionNoNumerica("USS", repo);
		Expresion expresion_500 = new ExpresionNumero(500);
		Expresion expresion_3 = new ExpresionNumero(3);
		Expresion expresionCompuesta = new ExpresionCompuesta(expresion_10, operadorMul, expresion_1000);
		Expresion expresionCompuesta2 = new ExpresionCompuesta(expresion_500, operadorRes, expresion_3);
		
		Expresion expresionSuperCompuesta = 
				new ExpresionCompuesta(expresionCompuesta, operadorSum, expresionCompuesta2);
		
		Assert.assertEquals(1497, expresionSuperCompuesta.calcular(unaEmpresa, "1988"), 0);
	}
	
	@Test
	public void parsearFormulaConCuentass(){
		Expresion exp = parser.obtenerExpresion("100 * USS + 1500 - 150");
		
		Empresa unaEmpresa = new Empresa("Domingo");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("USS", "1988", "100"));

		Assert.assertEquals(11350, exp.calcular(unaEmpresa, "1988"), 0);
	}
	
//	@Test
//	public void parsearFormulaConIndicador(){
//		Expresion exp = parser.obtenerExpresion("100 * popo + 1 - 1");
//		Empresa nuevo = new Empresa("popo", "6");
//		repo.agregar(nuevo);
//		Empresa unaEmpresa = new Empresa("Domingo");
//		unaEmpresa.cuentas = new ArrayList<>();
//		Assert.assertEquals(600, exp.calcular(unaEmpresa, "1988"), 0);
//	}
	
	@Test
	public void empresaDeFormatoArchivo(){
        String empresa = "{\"nombre\":\"Coca-cola2\", \"cuentas\":[{\"nombre\":\"juanca\",\"anio\":\"2000\",\"valor\":\"2000\"}]}";
        Empresa resultado =  conversor.deFormatoArchivo(empresa, Empresa.class);
        Assert.assertEquals(resultado.getNombre(), "Coca-cola2");
	}
	
	
	@Test
	public void empresaAFormatoArchivo(){
		Empresa unaEmpresa = new Empresa("Hello Bussiness world");
		unaEmpresa.setCuentas(new ArrayList<Cuenta>());
		unaEmpresa.agregarCuenta(new Cuenta("pepe", "111", "222"));
		String json = conversor.aFormatoArchivo(unaEmpresa);
        Assert.assertEquals(json, "{\"id\":0,\"nombre\":\"Hello Bussiness world\",\"cuentas\":[{\"id\":0,\"nombre\":\"pepe\",\"periodo\":\"111\",\"valor\":\"222\"}]}");
    }
	
   @Test
   public void deFormatoArchivoValido(){
        String empresa = "{nombre:\"Coca-cola\", \"cuentas\": [{\"nombre\": \"Juancito\", \"anio\": \"2017\", \"valor\":\"1525\"}]}";
        Empresa resultado =  conversor.deFormatoArchivo(empresa, Empresa.class);
        Assert.assertEquals(resultado.getNombre(), "Coca-cola");
    }

//    @Test
//    public void manejadorAgregaEmpresaAlRepoCorrectamente() {
//        Empresa nuevaEmpresa = new Empresa("popo");
//        nuevaEmpresa.setCuentas(new ArrayList<Cuenta>());
//        agregarEmpresaViewModel.setEmpresa("popo");
//        agregarEmpresaViewModel.agregarEmpresa();
//        Assert.assertTrue(agregarEmpresaViewModel.getRepoEmpresas().getLista().stream().anyMatch(x -> x.getNombre().equals("popo")));
//    }
    
//   @Test(expected = CuentaPreexistenteException.class)
//    public void viewModelAgregaCuentaRepetidaAlRepo(){
//    	agregarEmpresaViewModel.setEmpresa("W Up");
//    	agregarEmpresaViewModel.agregarEmpresa();
//    	Empresa empresa = agregarEmpresaViewModel.getRepoEmpresas().getLista().get(0);
//    	cuentasViewModel.setEmpresa(empresa);
//    	cuentasViewModel.setNombre("Patrimonio Neto");
//    	cuentasViewModel.setPeriodo("1000");
//    	cuentasViewModel.setValor("2000");
//    	cuentasViewModel.agregarCuenta();
//    	cuentasViewModel.agregarCuenta();
//    }

    /*@Test
    public void viewModelAgregarCuentaAlArchivoCorrectamente(){
    	agregarEmpresaViewModel.setEmpresa("Seven Up");
    	agregarEmpresaViewModel.agregarEmpresa();
    	Empresa empresa = manejador.getRepositorioEmpresas().getLista().get(0);
    	cuentasViewModel.setEmpresa(empresa);
    	cuentasViewModel.setNombre("Patrimonio Neto");
    	cuentasViewModel.setPeriodo("1000");
    	cuentasViewModel.setValor("2000");
    	cuentasViewModel.agregarCuenta();
    	Assert.assertEquals(manejador.getRepositorioEmpresas().getLista().get(0).getCuentas().get(0).getNombre(), "Patrimonio Neto");
    }*/
   
	@Test
	public void agregar_cuenta(){
		Empresa empresa1 = new Empresa("testEmpresa1");
		empresa1.setCuentas(new ArrayList<>());
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "20")); 
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "25"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); 
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
		
		Assert.assertEquals(empresa1.getCuentas().size(), 9);
	}
    
//    @Test(expected = CuentaInvalidaException.class)
//    public void cuentaConAnioInvalido(){
//    	cuentasViewModel.setPeriodo("./zxc");
//    }
//    
//    @Test(expected = CuentaInvalidaException.class)
//    public void cuentaConNombreInvalido(){
//    	cuentasViewModel.setNombre(null);
//    }
//    
//    @Test(expected = CuentaInvalidaException.class)
//    public void cuentaConValorInvalido(){
//    	cuentasViewModel.setValor(null);
//    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExtension(){
    	Archivo.validarRutaArchivo("rutaInvalida.tar");
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExistencia(){
    	Archivo.validarRutaArchivo("rutaInvalida.txt");
    }
  
    @After
    public void after(){
        file.delete();
    }
    
}

















