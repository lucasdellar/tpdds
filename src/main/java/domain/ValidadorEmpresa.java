package domain;

import domain.DomainExceptions.CuentaInvalidaException;
import domain.DomainExceptions.CuentaPreexistenteException;
import domain.DomainExceptions.EmpresaInvalidaException;
import domain.DomainExceptions.EmpresaYaCargadaException;

public class ValidadorEmpresa {

   public void validarNombre(String nombre) {
        if (nombre == null) throw new EmpresaInvalidaException("Debe ingresar un nombre.");
    }

	public void validarQueNoEsteYaCargarda(String nombre, RepositorioEmpresas repositorioEmpresas) {
    	if(repositorioEmpresas.getEmpresas().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new EmpresaYaCargadaException("La empresa ingresada ya existe.");

	}
	
}
