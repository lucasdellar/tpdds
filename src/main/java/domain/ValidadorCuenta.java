package domain;

import domain.DomainExceptions.CuentaInvalidaException;
import domain.DomainExceptions.CuentaPreexistenteException;

public class ValidadorCuenta {
	
    public void validarQueNoEsteYaCargarda(String nombre, RepositorioCuentas misCuentas) {
    	if(misCuentas.getCuentas().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new CuentaPreexistenteException("La cuenta ingresada ya existe.");
	}

    public void validarAnio(String anio) {
        if (anio == null || !isInteger(anio)) throw new CuentaInvalidaException("El anio debe ser un entero.");
    }

    private Boolean isInteger(String value){
        String intRegex = "[0-9]+";
        return value.matches(intRegex);
    }

    public void validarNombre(String nombre) {
        if (nombre == null) throw new CuentaInvalidaException("Debe ingresar un nombre.");
    }


    public void validarPatrimonio(String patrimonio_neto) {

        if (patrimonio_neto == null || !isFloatOrInt(patrimonio_neto) || Float.parseFloat(patrimonio_neto) < 0) throw new CuentaInvalidaException("El patrimonio neto debe ser un numero positivo");
    }

    private boolean isFloatOrInt(String patrimonio_neto) {
        String intRegex = "[0-9]+\\.[0-9]+";
        return patrimonio_neto.matches(intRegex) || isInteger(patrimonio_neto);
    }
}
