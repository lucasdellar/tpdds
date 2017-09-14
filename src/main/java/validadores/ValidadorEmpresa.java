package validadores;

import domain.DomainExceptions.EmpresaInvalidaException;
import domain.DomainExceptions.EmpresaYaCargadaException;
import repositorios.RepositorioEmpresas;

public class ValidadorEmpresa {

   public void validarNombre(String nombre) {
        if (nombre == null) throw new EmpresaInvalidaException("Debe ingresar un nombre.");
    }

	public void validarQueNoEsteYaCargarda(String nombre, RepositorioEmpresas repositorioEmpresas) {
    	if(repositorioEmpresas.nombreYaUtilizado(nombre)) throw new EmpresaYaCargadaException("La empresa ingresada ya existe.");
	}
	
}
