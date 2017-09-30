package validadores;

import domain.DomainExceptions.EmpresaInvalidaException;
import domain.DomainExceptions.EmpresaYaCargadaException;
import repositorios.RepositorioEmpresas;

public class ValidadorEmpresa {

   public void validarNombre(String nombre, RepositorioEmpresas repoEmpresas) {
        if (nombre == null || repoEmpresas.getLista().stream().anyMatch( x -> x.getNombre().equals(nombre))) throw new EmpresaInvalidaException("Debe ingresar un nombre que no esté en uso.");
    }

	public void validarQueNoEsteYaCargarda(String nombre, RepositorioEmpresas repositorioEmpresas) {
    	if(repositorioEmpresas.nombreYaUtilizado(nombre)) throw new EmpresaYaCargadaException("La empresa ingresada ya existe.");
	}
	
}
