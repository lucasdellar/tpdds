import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import OperacionesMatematicas.Multiplicacion;
import OperacionesMatematicas.Operador;
import OperacionesMatematicas.ResolutorDeCuentas;
import OperacionesMatematicas.Resta;
import OperacionesMatematicas.Suma;
import domain.Archivo;
import domain.ConversorFormatoArchivo;
import domain.Cuenta;
import domain.IConversorFormatoArchivo;
import domain.DomainExceptions.ArchivoInvalidoException;
import domain.DomainExceptions.CuentaInvalidaException;
import domain.DomainExceptions.CuentaPreexistenteException;
import domain.DomainExceptions.IndicadorInvalidoException;
import domain.DomainExceptions.ParserException;
import empresas.Empresa;
import expresiones.Expresion;
import expresiones.ExpresionCompuesta;
import expresiones.ExpresionCuenta;
import expresiones.ExpresionNumero;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import repositorios.RepositorioEmpresas;
import ui.ViewModels.AgregarEmpresaViewModel;
import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.InviertiendoViewModel;

public class Entrega2Test {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoEmpresas manejador;
    InviertiendoViewModel inviertiendoViewModel;
    CuentaViewModel cuentasViewModel;
    EmpresaViewModel empresaViewModel;
    AgregarEmpresaViewModel agregarEmpresaViewModel;
    File file;
	
	@Before
	public void initObjects() throws IOException{ 
		file = new File("cuentasMock.txt");
		file.createNewFile();
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoEmpresas("cuentasMock.txt");
        inviertiendoViewModel = new InviertiendoViewModel();
        cuentasViewModel = new CuentaViewModel();
        empresaViewModel = new EmpresaViewModel();
        Archivo archivo = new Archivo();
        archivo.setRuta("cuentasMock.txt");
        agregarEmpresaViewModel = new AgregarEmpresaViewModel(archivo);
        cuentasViewModel.setArchivo(archivo);
        empresaViewModel.setArchivo(archivo);
	}
	
	/* ***************************************** TESTS ENTREGA 2 & ENTREGA 3 ********************************************** */
	
	@Test
    public void verificarFormatoParser(){
		parser.Parser.verificarFormato("27");
	}
	
	@Test
    public void verificarFormatoParserConPalabra(){
		parser.Parser.verificarFormato(" otro + aasd + 5 / as");
	}
	
	@Test(expected = ParserException.class)
    public void parserExceptionPorFormato(){
		parser.Parser.verificarFormato("aa s27/ asd9*3+1 *3+ 5*dd4");
	}
	
	@Test
	public void expresionCuentaCalculaCorrectamente(){
		
		Empresa unaEmpresa = new Empresa("test");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("patrimonio", "2017", "100"));
		
		Expresion expresion = new ExpresionCuenta("patrimonio");
		Assert.assertEquals(100, expresion.calcular(unaEmpresa, "2017"), 0);
	}

	@Test (expected = IndicadorInvalidoException.class)
	public void expresionCuentaFallaPorFaltaDeDatos(){
		
		Empresa unaEmpresa = new Empresa("test");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("patrimonio", "2017", "100"));
		
		Expresion expresion = new ExpresionCuenta("fondos");
		Assert.assertEquals(100, expresion.calcular(unaEmpresa, "2017"), 0);
	}
	
	@Test
	public void expresionCompuestaCalculaCorrectamente(){
		
		// Cálculo: 10 * 100 = 1000.
		
		Empresa unaEmpresa = new Empresa("Domingo");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("USS", "2000", "100"));
		
		Expresion expresion1 = new ExpresionNumero(10);
		Operador operador = new Multiplicacion();
		Expresion expresion2 = new ExpresionCuenta("USS");
		Expresion expresionCompuesta = new ExpresionCompuesta(expresion1, operador, expresion2);
		
		Assert.assertEquals(1000, expresionCompuesta.calcular(unaEmpresa, "2000"), 0);
	}
	
	@Test 
	public void expresionesCompuestasConExpresionesCompuestas(){
		
		// Cálculo: 10 * 100 + 500 - 3 = 1497.
		
		Empresa unaEmpresa = new Empresa("Domingo");
		unaEmpresa.cuentas = new ArrayList<>();
		unaEmpresa.agregarCuenta(new Cuenta("USS", "1988", "100"));
		
		Operador operadorMul = new Multiplicacion();
		Operador operadorSum = new Suma();
		Operador operadorRes = new Resta();
		Expresion expresion_10 = new ExpresionNumero(10);
		Expresion expresion_1000 = new ExpresionCuenta("USS");
		Expresion expresion_500 = new ExpresionNumero(500);
		Expresion expresion_3 = new ExpresionNumero(3);
		Expresion expresionCompuesta = new ExpresionCompuesta(expresion_10, operadorMul, expresion_1000);
		Expresion expresionCompuesta2 = new ExpresionCompuesta(expresion_500, operadorRes, expresion_3);
		
		Expresion expresionSuperCompuesta = 
				new ExpresionCompuesta(expresionCompuesta, operadorSum, expresionCompuesta2);
		
		Assert.assertEquals(1497, expresionSuperCompuesta.calcular(unaEmpresa, "1988"), 0);
	}
	
	
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
        Assert.assertEquals(json, "{\"nombre\":\"Hello Bussiness world\",\"cuentas\":[{\"nombre\":\"pepe\",\"periodo\":\"111\",\"valor\":\"222\"}]}");
    }
	
   @Test
   public void deFormatoArchivoValido(){
        String empresa = "{nombre:\"Coca-cola\", \"cuentas\": [{\"nombre\": \"Juancito\", \"anio\": \"2017\", \"valor\":\"1525\"}]}";
        Empresa resultado =  conversor.deFormatoArchivo(empresa, Empresa.class);
        Assert.assertEquals(resultado.getNombre(), "Coca-cola");
    }

    @Test
    public void manejadorAgregaEmpresaAlArchivoCorrectamente() {
        Empresa nuevaEmpresa = new Empresa("popo");
        manejador.agregarEmpresaAlArchivo(nuevaEmpresa);
        RepositorioEmpresas repositorioEmpresas = manejador.getRepositorioEmpresas();
        Assert.assertEquals(repositorioEmpresas.getLista().get(0).getNombre(), "popo");
    }
    
   @Test(expected = CuentaPreexistenteException.class)
    public void viewModelAgregaCuentaRepetidaAlArchivo(){
    	agregarEmpresaViewModel.setEmpresa("Seven Up");
    	agregarEmpresaViewModel.agregarEmpresa();
    	Empresa empresa = manejador.getRepositorioEmpresas().getLista().get(0);
    	cuentasViewModel.setEmpresa(empresa);
    	cuentasViewModel.setNombre("Patrimonio Neto");
    	cuentasViewModel.setPeriodo("1000");
    	cuentasViewModel.setValor("2000");
    	cuentasViewModel.agregarCuenta();
    	cuentasViewModel.agregarCuenta();
    }

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
    
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConAnioInvalido(){
    	cuentasViewModel.setPeriodo("./zxc");
    }
    
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConNombreInvalido(){
    	cuentasViewModel.setNombre(null);
    }
    
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConValorInvalido(){
    	cuentasViewModel.setValor(null);
    }
    
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

















