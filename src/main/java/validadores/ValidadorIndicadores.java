package validadores;

import domain.DomainExceptions.IndicadorInexsistenteException;
import domain.DomainExceptions.IndicadorYaCreadoExcepction;
import manejadoresArchivo.ManejadorDeArchivoIndicadores;
import repositorios.RepositorioIndicadores;

public class ValidadorIndicadores {

	  public void validarFormula(String formula) {
		  //parser.Parser.verificarFormato(formula);
	    }

		public void validarQueNoEsteYaCargardo(String nombre, String formula, RepositorioIndicadores repo) {
			if(repo.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new IndicadorYaCreadoExcepction("El indicador ingresado ya existe.");
		}

		public void validarIndicador(String nombre, String formula, RepositorioIndicadores repo) {
			this.validarFormula(formula);
			this.validarQueNoEsteYaCargardo(nombre, formula, repo);
		}
	
		public void validarQueExista(String nombre, RepositorioIndicadores repo) {
			if(!repo.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre))) 
				throw new IndicadorInexsistenteException("Indicador no cargado");
		}
}
