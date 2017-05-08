
import domain.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.UserException;
import ui.*;

public class ConversorFormatoArchivoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoCuentas manejador;
    Class<Cuenta> claseCuenta;
    InviertiendoViewModel viewModel;
	
	@Before
	public void initObjects(){ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoCuentas("cuentasMock.txt");
        claseCuenta = Cuenta.class;
        viewModel = new InviertiendoViewModel();
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
    	RepositorioCuentas repo = new RepositorioCuentas();
    	repo.agregarCuenta(new Cuenta("Hola", "2020", "1111.0"));
    	viewModel.setRepositorioCuentas(repo);
    	viewModel.setNuevaCuenta(new Cuenta("Hola", "2018", "300.0"));
    	viewModel.agregarCuenta();
    }
    
    @Test(expected = AnioInvalidoException.class)
    public void anioInvalido(){
    	viewModel.setNuevaCuenta(new Cuenta("Hola", "asd", "300"));
    	viewModel.agregarCuenta();
    }
    
    @Test(expected = NombreInvalidoException.class)
    public void nombreInvalido(){
    	viewModel.setNuevaCuenta(new Cuenta(null, "asd", "300"));
    	viewModel.agregarCuenta();
    }
    
    @Test(expected = PatrimonioInvalidoException.class)
    public void patrimonioInvalido(){
    	viewModel.setNuevaCuenta(new Cuenta("Nombre", "123", "asd"));
    	viewModel.agregarCuenta();
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExtension(){
    	viewModel.setRutaArchivo("rutaInvalida.tx");
    	viewModel.mostrarCuentas();
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExistencia(){
    	viewModel.setRutaArchivo("rutaInvalida");
    	viewModel.mostrarCuentas();
    }
    
    
    
    @After
    public void after(){
        // manejador.getArchivo().delete();
    }
    
}

















