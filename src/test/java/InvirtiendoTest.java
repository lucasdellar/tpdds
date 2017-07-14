
import domain.*;

import domain.DomainExceptions.*;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import parser.*;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioIndicadores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import OperacionesMatematicas.ResolutorDeCuentas;
import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.ComparaEmpresasPorIndicador;
import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import condiciones.Crecimiento;
import condiciones.DependeDeValor;
import criterios.CrecimientoSiempre;
import criterios.CriterioCrecimiento;
import criterios.Promedio;
import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.InviertiendoViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.AgregarEmpresaViewModel;

public class InvirtiendoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoEmpresas manejador;
    InviertiendoViewModel inviertiendoViewModel;
    CuentaViewModel cuentasViewModel;
    EmpresaViewModel empresaViewModel;
    AgregarEmpresaViewModel agregarEmpresaViewModel;
    File file;
	
	@Before
	public void initObjects() throws IOException{ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
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
	
	@Test
	public void comparadorMenor(){
		ComparadorMenor comparador = new ComparadorMenor();
		
		Assert.assertEquals(comparador.comparar(5, 9), true);
	}
	
	@Test
	public void comparadorMayor(){
		ComparadorMayor comparador = new ComparadorMayor();
		
		Assert.assertEquals(comparador.comparar(5, 9), false);
	}
	
	@Test
	public void aplicar_Taxativa(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		CondicionTaxativa taxativa = new CondicionTaxativa(repo, new ComparadorMenor(), 100);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		taxativa.setCriterio(new Promedio(unIndicador));
		Empresa miEmpresa = new Empresa("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));	
		Assert.assertTrue(taxativa.aplicar(miEmpresa));
	}
	
	@Test
	public void compararEmpresasPorIndicador(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		Indicador unIndicador = new Indicador("testIndicador", "cuentaTest + 5");
		ComparaEmpresasPorIndicador compararEmpresasPorIndicador = new ComparaEmpresasPorIndicador(repo, new ComparadorMayor(),
				unIndicador, "2015");
		
		EmpresaRankeada empresaUno = new EmpresaRankeada("empresaTestUno");
		empresaUno.setCuentas(new ArrayList());
		empresaUno.agregarCuenta(new Cuenta("cuentaTest", "2015", "1300"));
		
		EmpresaRankeada empresaDos = new EmpresaRankeada("empresaTestDos");
		empresaDos.setCuentas(new ArrayList());
		empresaDos.agregarCuenta(new Cuenta("cuentaTest", "2015", "500"));
		
		EmpresaRankeada empresaTres = new EmpresaRankeada("empresaTestTres");
		empresaTres.setCuentas(new ArrayList());
		empresaTres.agregarCuenta(new Cuenta("cuentaTest", "2015", "5050"));
		
		
		ArrayList<EmpresaRankeada> empresas = new ArrayList<>();
		empresas.add(empresaUno);
		empresas.add(empresaDos);
		empresas.add(empresaTres);
		
		compararEmpresasPorIndicador.setPeso(10);
		compararEmpresasPorIndicador.aplicar(empresas);
		
		for(EmpresaRankeada empresa : empresas){
			System.out.println(empresa.getNombre());
		}
		
		Assert.assertEquals(empresas.get(0).getNombre(), empresaTres.getNombre());
	}
	
	@Test
	public void metodologiaOrdenaCorrectamentePorPesoLasEmpresas(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa1");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.aumentarRanking(10);
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		EmpresaRankeada miEmpresa2 = new EmpresaRankeada("testEmpresa2");
		miEmpresa2.setCuentas(new ArrayList<>());
		miEmpresa2.aumentarRanking(15);
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		EmpresaRankeada miEmpresa3 = new EmpresaRankeada("testEmpresa3");
		miEmpresa3.setCuentas(new ArrayList<>());
		miEmpresa3.aumentarRanking(5);
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<>();
		empresas.add(miEmpresa);
		empresas.add(miEmpresa2);
		empresas.add(miEmpresa3);
		Metodologia metodologiaTest = new Metodologia("testMetodologia", new ArrayList<>());
		metodologiaTest.ordenarPorRanking(empresas);
		Assert.assertEquals(empresas.get(0).getNombre(), "testEmpresa3");
		Assert.assertEquals(empresas.get(1).getNombre(), "testEmpresa1");
		Assert.assertEquals(empresas.get(2).getNombre(), "testEmpresa2");
	}
	
	
	@Test
	public void condicionDependeDeValorEvaluaCorrectamenteUnaEmpresa(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		DependeDeValor dependeDeValor = new DependeDeValor(repo, new ComparadorMenor(), 100);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		dependeDeValor.setCriterio(new Promedio(unIndicador));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));
		empresas.add(miEmpresa);		
		Assert.assertEquals(dependeDeValor.aplicar(empresas).size(), 1);
	}
	
	@Test
	public void condicionCrecimientoEvaluaCorrectamenteUnaEmpresa(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		Crecimiento crece = new Crecimiento(repo, new ComparadorMenor());
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		crece.setCriterio(new CrecimientoSiempre(unIndicador, 2015, 2017));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));
		empresas.add(miEmpresa);		
		Assert.assertEquals(crece.aplicar(empresas).size(), 1);
	}	
	
	@Test
	public void metodologiaConCondicionesAceptaCrecimiento(){
		Crecimiento crece = new Crecimiento(new RepositorioIndicadores(), new ComparadorMayor());
		ArrayList<Condicion> lista = new ArrayList<>();
		lista.add(crece);
		Metodologia metodologia = new Metodologia("testMetodologia", lista);
		Assert.assertEquals(metodologia.getCondiciones().size(), 1);
	}
	
	// TEST's PREVIOS ------------------------------------------
	
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
	public void resolutorResuelveCorrectamenteLaCuenta(){
        Assert.assertEquals(ResolutorDeCuentas.resolver("27/9*3+ 1*3+5  *4"), 32, 0);
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


    @Test
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

















