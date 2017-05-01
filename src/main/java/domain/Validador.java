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
    	if(misCuentas.getCuentas().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new UserException("La cuenta ingresada ya existe.");
	}

	private static void validarAnio(String anio) throws UserException{
       if (anio == null || !isInteger(anio)) throw new UserException("El anio debe ser un entero.");
    }

    private static Boolean isInteger(String value){
        String intRegex = "[0-9]+";
        return value.matches(intRegex);
    }

    private static void validarNombre(String nombre) {
        if (nombre == null) throw new UserException("Debe ingresar un nombre.");
    }


    private static void validarPatrimonio(String patrimonio_neto) {
        String intRegex = "[0-9]+\\.[0-9]+";
        if (patrimonio_neto == null || Float.parseFloat(patrimonio_neto) < 0) throw new UserException("El patrimonio neto debe ser un numero positivo");
    }

	public static void validarRutaArchivo(String rutaArchivo) {
        String archRegex = ".+\\.txt";
        if (rutaArchivo == null || !rutaArchivo.matches(archRegex)) throw new UserException("El nombre del archivo no es valido. Debe ser un .txt");
        if (!new File(rutaArchivo).exists()) throw new UserException("El archivo ingresado no existe. Ingrese otro nombre o creelo y vuelva a intentarlo.");
	}
}
