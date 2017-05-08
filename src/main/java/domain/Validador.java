package domain;

import java.io.File;

import org.uqbar.commons.model.UserException;

public class Validador {


    public static void validarCuenta(Cuenta unaCuenta, RepositorioCuentas misCuentas){
        validarNombre(unaCuenta.getNombre());
        validarAnio(unaCuenta.getAnio());
        validarPatrimonio(unaCuenta.getPatrimonio_neto());
        validarQueNoEsteYaCargarda(unaCuenta.getNombre(), misCuentas);
    }

    private static void validarQueNoEsteYaCargarda(String nombre, RepositorioCuentas misCuentas) {
    	if(misCuentas.getCuentas().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new CuentaPreexistenteException("La cuenta ingresada ya existe.");
	}

	public static void validarAnio(String anio) {
       if (anio == null || !isInteger(anio)) throw new AnioInvalidoException("El anio debe ser un entero.");
    }

    private static Boolean isInteger(String value){
        String intRegex = "[0-9]+";
        return value.matches(intRegex);
    }

    public static void validarNombre(String nombre) {
        if (nombre == null) throw new NombreInvalidoException("Debe ingresar un nombre.");
    }


    public static void validarPatrimonio(String patrimonio_neto) {
        
        if (patrimonio_neto == null || !isFloatOrInt(patrimonio_neto) || Float.parseFloat(patrimonio_neto) < 0) throw new PatrimonioInvalidoException("El patrimonio neto debe ser un numero positivo");
    }

	private static boolean isFloatOrInt(String patrimonio_neto) {
		String intRegex = "[0-9]+\\.[0-9]+";
		return patrimonio_neto.matches(intRegex) || isInteger(patrimonio_neto);
	}

	public static void validarRutaArchivo(String rutaArchivo) {
        String archRegex = ".+\\.txt";
        if (rutaArchivo == null || !rutaArchivo.matches(archRegex)) throw new ArchivoInvalidoException("El nombre del archivo no es valido. Debe ser un .txt");
        if (!new File(rutaArchivo).exists()) throw new ArchivoInvalidoException("El archivo ingresado no existe. Ingrese otro nombre o creelo y vuelva a intentarlo.");
	}
}
