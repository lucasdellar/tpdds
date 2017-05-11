
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

    IConversorFormatoArchivo<Cuenta> conversor;
    ManejadorDeArchivoCuentas manejador;
    Class<Cuenta> claseCuenta;
    InviertiendoViewModel inviertiendoViewModel;
    CuentaViewModel cuentasViewModel;
    File file;
	
	@Before
	public void initObjects() throws IOException{ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
		file = new File("cuentasMock.txt");
		file.createNewFile();
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoCuentas("cuentasMock.txt");
        claseCuenta = Cuenta.class;
        inviertiendoViewModel = new InviertiendoViewModel();
        cuentasViewModel = new CuentaViewModel();
	}
	
   @Test
   public void testDeFormatoArchivo(){
        String cuenta = "{ nombre : \"juanca\", anio : \"2000\",patrimonio_neto : \"2000\"}";
        Cuenta resultado =  conversor.deFormatoArchivo(cuenta, claseCuenta);
        Assert.assertEquals(resultado.getNombre(), "juanca");
    }

    @Test
    public void agregarCuentaAlArchivo() {
        Cuenta nuevaCuenta = new Cuenta("mati", "2017", "99999");
        manejador.agregarCuentaAlArchivo(nuevaCuenta);
        RepositorioCuentas repositorioCuentas = manejador.getRepositorioCuentas();
        Assert.assertEquals(repositorioCuentas.getCuentas().get(1).getNombre(), "mati");
    }
    
    @Test(expected = CuentaPreexistenteException.class)
    public void cuentaRepetida(){
    	Archivo archivo = new Archivo();
    	archivo.setRuta("cuentasMock.txt");
    	manejador.agregarCuentaAlArchivo(new Cuenta("Hola", "2020", "1111"));
    	cuentasViewModel.setArchivo(archivo);
    	cuentasViewModel.setNombre("Hola");
    	cuentasViewModel.setAnio("1233"); 
    	cuentasViewModel.setPatrimonio_neto("1233"); 
    	cuentasViewModel.agregarCuenta();
    }
    
    @Test(expected = AnioInvalidoException.class)
    public void anioInvalido(){
    	cuentasViewModel.setAnio("./zxc");
    	//viewModel.agregarCuenta();
    }
    
    @Test(expected = NombreInvalidoException.class)
    public void nombreInvalido(){
    	cuentasViewModel.setNombre(null);
    }
    
    @Test(expected = PatrimonioInvalidoException.class)
    public void patrimonioInvalido(){
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
        // manejador.getArchivo().delete();
    }
    
}

















