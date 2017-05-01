
import domain.*;
import domain.RepositorioCuentas;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Matias Fischer on 27/04/2017.
 */

public class ConversorFormatoArchivoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoCuentas manejador;
    Class<Cuenta> claseCuenta;
	
	@Before
	public void initObjects(){ // Si creamos los objetos aca no funciona los mantiene al salir del metodo.
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoCuentas("cuentasMock.txt");
        claseCuenta = Cuenta.class;
	}
	
    @Test
    public void testDeFormatoArchivo(){
        String cuenta = "{ nombre = \"juanca\", anio = \"2000\",patrimonio_neto = \"2000\"}";
        Cuenta resultado =  conversor.deFormatoArchivo(cuenta, claseCuenta);
        Assert.assertEquals(resultado.getNombre(), "juanca");
    }

    @Test
    public void agregarCuentaAlArchivo() {
        Cuenta nuevaCuenta = new Cuenta("mati", "2017", "99999");
        manejador.agregarCuentaAlArchivo(nuevaCuenta);
        RepositorioCuentas repositorioCuentas = manejador.getRepositorioCuentas();
        Assert.assertEquals(repositorioCuentas.getCuentas().get(0).getNombre(), "mati");
    }
    
    @After
    public void after(){
        manejador.getArchivo().delete();
    }
    
}

















