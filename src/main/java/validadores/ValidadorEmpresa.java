package validadores;

import domain.DomainExceptions.EmpresaInvalidaException;
import domain.DomainExceptions.EmpresaYaCargadaException;
import repositorios.RepositorioEmpresas;

public class ValidadorEmpresa {

   public Boolean validarNombre(String nombre, RepositorioEmpresas repoEmpresas) {
        return nombre == null || repoEmpresas.getLista().stream().anyMatch( x -> x.getNombre().equals(nombre));
    }

	public void validarQueNoEsteYaCargarda(String nombre, RepositorioEmpresas repositorioEmpresas) {
    	if(repositorioEmpresas.nombreYaUtilizado(nombre)) throw new EmpresaYaCargadaException("La empresa ingresada ya existe.");
	}
	
}
