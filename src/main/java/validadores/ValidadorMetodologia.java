package validadores;

import domain.DomainExceptions.MetodologiaInvalidaException;
import model.Metodologia;
import repositorios.RepositorioMetodologias;

public class ValidadorMetodologia {

	public void validar(Metodologia meto, RepositorioMetodologias repo) {
		if(repo.getLista().stream().anyMatch(x -> x.getNombre().equals(meto.getNombre()))) throw new MetodologiaInvalidaException("Ya existe una metodologia con ese nombre");
		if(meto.getCondiciones_prioritarias().isEmpty() && meto.getCondiciones_taxativas().isEmpty()) throw new MetodologiaInvalidaException("La metodologia debe tener condiciones.");
	}
	
}
