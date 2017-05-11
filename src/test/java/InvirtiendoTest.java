
import domain.*;

import domain.DomainExceptions.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.InviertiendoViewModel;

public class InvirtiendoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoCuentas manejador;
    InviertiendoViewModel inviertiendoViewModel;
    CuentaViewModel cuentasViewModel;
    File file;
	
	@Before
	public void initObjects() throws IOException{ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
		file = new File("cuentasMock.txt");
		file.createNewFile();
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoCuentas("cuentasMock.txt");
        inviertiendoViewModel = new InviertiendoViewModel();
        cuentasViewModel = new CuentaViewModel();
        Archivo archivo = new Archivo();
        archivo.setRuta("cuentasMock.txt");
        cuentasViewModel.setArchivo(archivo);
	}
	
   @Test
   public void deFormatoArchivoValido(){
        String cuenta = "{ nombre : \"juanca\", anio : \"2000\",patrimonio_neto : \"2000\"}";
        Cuenta resultado =  conversor.deFormatoArchivo(cuenta);
        Assert.assertEquals(resultado.getNombre(), "juanca");
    }

    @Test
    public void manejadorAgregaCuentaAlArchivoCorrectamente() {
        Cuenta nuevaCuenta = new Cuenta("mati", "2017", "99999");
        manejador.agregarCuentaAlArchivo(nuevaCuenta);
        RepositorioCuentas repositorioCuentas = manejador.getRepositorioCuentas();
        Assert.assertEquals(repositorioCuentas.getCuentas().get(0).getNombre(), "mati");
    }
    
    @Test(expected = CuentaPreexistenteException.class)
    public void viewModelAgregaCuentaRepetidaAlArchivo(){
    	cuentasViewModel.setNombre("Hola");
    	cuentasViewModel.setAnio("1233"); 
    	cuentasViewModel.setPatrimonio_neto("1233"); 
    	cuentasViewModel.agregarCuenta();
    	cuentasViewModel.setNombre("Hola");
    	cuentasViewModel.setAnio("1233"); 
    	cuentasViewModel.setPatrimonio_neto("1233"); 
    	cuentasViewModel.agregarCuenta();
    }
    
    @Test
    public void viewModelAgregaCuentaAlArchivoCorrectamente(){
    	cuentasViewModel.setNombre("koko13");
    	cuentasViewModel.setAnio("1233"); 
    	cuentasViewModel.setPatrimonio_neto("1233"); 
    	cuentasViewModel.agregarCuenta();
    	Assert.assertEquals(manejador.getRepositorioCuentas().getCuentas().get(0).getNombre(), "koko13");
    }
    
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
    public void cuentaConPatrimonioInvalido(){
    	cuentasViewModel.setPatrimonio_neto(null);
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

















