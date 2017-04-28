
import domain.ConversorFormatoArchivo;
import domain.Cuenta;
import domain.Cuentas;
import domain.IConversorFormatoArchivo;
import domain.ManejadorDeArchivoCuentas;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Matias Fischer on 27/04/2017.
 */

class Fixture {

    public static Class<Cuenta> getClaseDeCuenta(){
        return Cuenta.class;
    }

    public static IConversorFormatoArchivo getConversor(){
        return new ConversorFormatoArchivo();
    }
    
    public static ManejadorDeArchivoCuentas getManejador() throws Exception{
    	return new ManejadorDeArchivoCuentas("cuentasMock.txt", getConversor());
    }
    
 }


public class ConversorFormatoArchivoTest {


    @Test
    public void testDeFormatoArchivo() throws Exception {
        Class<Cuenta> claseCuenta = Fixture.getClaseDeCuenta();
        IConversorFormatoArchivo conversor = Fixture.getConversor();

        String cuenta = "{ nombre = \"juanca\", anio = \"2000\",patrimonio_neto = \"2000\"}";

        Cuenta resultado =  conversor.DeFormatoArchivo(cuenta, claseCuenta);

        //Assert.assertTrue(resultado.getNombre() == "juanca");        
        Assert.assertEquals(resultado.getNombre(), "juanca");

    }

    @Test
    public void agregarCuentaAlArchivo() throws Exception {
        IConversorFormatoArchivo conversor = Fixture.getConversor();
        ManejadorDeArchivoCuentas manejador = Fixture.getManejador();
        Cuenta nuevaCuenta = new Cuenta("mati", "2017", "99999");
        manejador.agregarCuentaAlArchivo(nuevaCuenta);
        Cuentas cuentas = manejador.getCuentas();
        Assert.assertEquals(cuentas.getCuentas().get(0).getNombre(), "mati");
        manejador.getArchivo().delete();
    }
    
    @After
    public void After(){
    }
    
}

















