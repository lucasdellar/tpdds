package validadores;

import domain.DomainExceptions.EmpresaInvalidaException;
import domain.DomainExceptions.EmpresaYaCargadaException;
import repositorios.RepositorioEmpresas;

public class ValidadorEmpresa {

   public Boolean validarNombre(String nombre, RepositorioEmpresas repoEmpresas) {
        return nombre == null || repoEmpresas.nombreYaUtilizado(nombre);
    }

}
