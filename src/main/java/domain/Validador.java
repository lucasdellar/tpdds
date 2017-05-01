package domain;

import org.uqbar.commons.model.UserException;

public class Validador {


    public static void validarCuenta(Cuenta unaCuenta){
        validarAnio(unaCuenta.getAnio());
        validarNombre(unaCuenta.getNombre());
        validarPatrimonio(unaCuenta.getPatrimonio_neto());
    }

    private static void validarAnio(String anio) throws UserException{
       if (!isInteger(anio)) throw new UserException("El anio debe ser un entero");
    }

    private static Boolean isInteger(String value){
        String intRegex = "[0-9]+";
        return value.matches(intRegex);
    }

    private static void validarNombre(String nombre) {
        if (nombre.length() > 30) throw new UserException("El nombre es demasiado largo.");
    }


    private static void validarPatrimonio(String patrimonio_neto) {
        String intRegex = "[0-9]+\\.[0-9]+";
        if (Float.parseFloat(patrimonio_neto) >= 0) throw new UserException("El nombre es demasiado largo.");
    }
}
