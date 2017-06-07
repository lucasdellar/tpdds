
import domain.*;

import domain.DomainExceptions.*;
import parser.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.InviertiendoViewModel;

public class InvirtiendoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoEmpresas manejador;
    InviertiendoViewModel inviertiendoViewModel;
    CuentaViewModel cuentasViewModel;
    File file;
	
	@Before
	public void initObjects() throws IOException{ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
		file = new File("cuentasMock.txt");
		file.createNewFile();
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoEmpresas("cuentasMock.txt");
        inviertiendoViewModel = new InviertiendoViewModel();
        cuentasViewModel = new CuentaViewModel();
        Archivo archivo = new Archivo();
        archivo.setRuta("cuentasMock.txt");
        cuentasViewModel.setArchivo(archivo);
	}
	
	
	@Test
    public void verificarFormatoParser(){
		parser.Parser.verificarFormato("27");
	}
	
	@Test
    public void verificarFormatoParserConPalabra(){
		parser.Parser.verificarFormato("otro + aasd + 5 / as");
	}
	
	@Test(expected = ParserException.class)
    public void parserExceptionPorFormato(){
		parser.Parser.verificarFormato(" aa s27/ asd9*3+1 *3+ 5*dd4");
	}
	
	@Test
	public void parserResuelveCorrectamenteLaCuenta(){
        Assert.assertEquals(parser.Parser.resolverCuenta("27/9*3+ 1*3+5  *4"), 32, 0);
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
        Assert.assertEquals(json, "{\"nombre\":\"Hello Bussiness world\",\"cuentas\":[{\"nombre\":\"pepe\",\"anio\":\"111\",\"valor\":\"222\"}]}");
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
        Assert.assertEquals(repositorioEmpresas.getEmpresas().get(0).getNombre(), "popo");
    }
    
   /* @Test(expected = CuentaPreexistenteException.class)
    public void viewModelAgregaCuentaRepetidaAlArchivo(){
    	Empresa empresa = new Empresa("McDonald's");
    	empresa.agregarCuenta(new Cuenta("El patrimonio pap�", "1000", "2000"));
    	cuentasViewModel.setEmpresa(empresa);
    	cuentasViewModel.setNombre("El patrimonio pap�");
    	cuentasViewModel.setAnio("1000");
    	cuentasViewModel.setValor("2000");
    	cuentasViewModel.agregarCuenta();
    }
    */
    /*
    @Test
    public void viewModelAgregarCuentaAlArchivoCorrectamente(){
    	Empresa empresa = new Empresa("Burger King");
    	empresa.agregarCuenta(new Cuenta("Coco", "5", "15000"));
    	cuentasViewModel.setEmpresa(empresa);
    	cuentasViewModel.setNombre("Coco");
    	cuentasViewModel.setAnio("5"); 
    	cuentasViewModel.setValor("15000"); 
    	cuentasViewModel.agregarCuenta();
    	Assert.assertEquals(manejador.getRepositorioEmpresas().getEmpresas().get(0).getCuentas().get(0).getNombre(), "Coco");
    }
    
    */
    
    /*
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConAnioInvalido(){
    	cuentasViewModel.setAnio("./zxc");
    	//viewModel.agregarCuenta();
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
    */
    @After
    public void after(){
        file.delete();
    }
    
}

















