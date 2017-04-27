package Tests;

import com.google.gson.Gson;
import domain.ConversorFormatoArchivo;
import domain.Cuenta;
import domain.IConversorFormatoArchivo;
import junit.framework.TestCase;
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
 }


public class ConversorFormatoArchivoTest {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testDeFormatoArchivo() throws Exception {
        Class<Cuenta> claseCuenta = Fixture.getClaseDeCuenta();
        IConversorFormatoArchivo conversor = Fixture.getConversor();

        String cuenta = "{ nombre = 'juanca', anio = '2000',patrimonio_neto = '2000'}";

        Cuenta resultado =  conversor.DeFormatoArchivo(cuenta, claseCuenta);

        Assert.assertTrue(resultado.getNombre() == "juanca");
    }


}