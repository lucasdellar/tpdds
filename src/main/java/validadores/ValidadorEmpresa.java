package validadores;

import domain.Empresa;
import domain.DomainExceptions.EmpresaInvalidaException;
import domain.DomainExceptions.EmpresaYaCargadaException;
import repositorios.Repositorio;

public class ValidadorEmpresa {

   public void validarNombre(String nombre) {
        if (nombre == null) throw new EmpresaInvalidaException("Debe ingresar un nombre.");
    }

	public void validarQueNoEsteYaCargarda(String nombre, Repositorio<Empresa> repositorioEmpresas) {
    	if(repositorioEmpresas.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new EmpresaYaCargadaException("La empresa ingresada ya existe.");
	}
	
}
