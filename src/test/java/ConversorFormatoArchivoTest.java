
import domain.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.UserException;

public class ConversorFormatoArchivoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoCuentas manejador;
    Class<Cuenta> claseCuenta;
    Class<Validador> validador;
	
	@Before
	public void initObjects(){ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoCuentas("cuentasMock.txt");
        validador = Validador.class;
        claseCuenta = Cuenta.class;
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
    	RepositorioCuentas repositorioCuentas = manejador.getRepositorioCuentas();
    	Cuenta nuevaCuenta = new Cuenta("CocaCola", "2018", "300");
    	manejador.agregarCuentaAlArchivo(nuevaCuenta);
    	Validador.validarCuenta(nuevaCuenta, repositorioCuentas);
    }
    
    @Test(expected = AnioInvalidoException.class)
    public void anioInvalido(){
    	Cuenta nuevaCuenta = new Cuenta("Roar", "Doscientos A.C", "333");
    	Validador.validarAnio(nuevaCuenta.getAnio());
    }
    
    @Test(expected = NombreInvalidoException.class)
    public void nombreInvalido(){
    	Cuenta nuevaCuenta = new Cuenta(null, "300", "8");
    	Validador.validarNombre(nuevaCuenta.getNombre());
    }
    
    @Test(expected = PatrimonioInvalidoException.class)
    public void patrimonioInvalido(){
    	Cuenta nuevaCuenta = new Cuenta("Pablito Mendez", "2010", "-957");
    	Validador.validarPatrimonio(nuevaCuenta.getPatrimonio_neto());
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExtension(){
    	Validador.validarRutaArchivo("archivoCualquiera.xml");
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExistencia(){
    	Validador.validarRutaArchivo("archivoCualquiera.txt");
    }
    
    
    
    @After
    public void after(){
        //manejador.getArchivo().delete();
    }
    
}

















