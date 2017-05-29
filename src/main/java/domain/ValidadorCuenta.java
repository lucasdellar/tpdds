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


    public void validarValor(String valor) {

        if (valor == null || !isFloatOrInt(valor) || Float.parseFloat(valor) < 0) throw new CuentaInvalidaException("El valor debe ser un numero positivo");
    }

    private boolean isFloatOrInt(String valor) {
        String intRegex = "[0-9]+\\.[0-9]+";
        return valor.matches(intRegex) || isInteger(valor);
    }
    
    public void validarEmpresa(String empresa) {
    	if(empresa == null) throw new CuentaInvalidaException("Debe ingresar una empresa");
    }
}
