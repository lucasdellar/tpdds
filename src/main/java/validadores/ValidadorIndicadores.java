package validadores;

import parser.Parser;
import repositorios.RepositorioIndicadores;

public class ValidadorIndicadores {

	  public boolean esValidaLaFormula(String formula, RepositorioIndicadores repo) {
		  return new Parser(repo).verificarFormato(formula);
	    }

		public boolean estaCargado(String nombre, String formula, RepositorioIndicadores repo) {
			return repo.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre));
		}

		public boolean esValido(String nombre, String formula, RepositorioIndicadores repo) {
			return this.esValidaLaFormula(formula, repo) || !this.estaCargado(nombre, formula, repo);
		}
	
		public boolean noExiste(String nombre, RepositorioIndicadores repo) {
			return !repo.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre));
		}
}
