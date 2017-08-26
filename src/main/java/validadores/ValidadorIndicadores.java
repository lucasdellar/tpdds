package validadores;

import domain.DomainExceptions.IndicadorInexsistenteException;
import domain.DomainExceptions.IndicadorYaCreadoExcepction;
import manejadoresArchivo.ManejadorDeArchivoIndicadores;
import repositorios.RepositorioIndicadores;

public class ValidadorIndicadores {

	  public void validarFormula(String formula) {
		  //parser.Parser.verificarFormato(formula);
	    }

		public void validarQueNoEsteYaCargardo(String nombre, String formula, ManejadorDeArchivoIndicadores manejador) {
			if(manejador.getRepositorioIndicadores().getLista().stream().anyMatch(x -> x.getNombre().equals(nombre))) throw new IndicadorYaCreadoExcepction("El indicador ingresado ya existe.");
 
		}

		public void validarIndicador(String nombre, String formula, ManejadorDeArchivoIndicadores manejador) {
			this.validarFormula(formula);
			this.validarQueNoEsteYaCargardo(nombre, formula, manejador);
		}
	
		public void validarQueExista(String nombre, RepositorioIndicadores repo) {
			if(!repo.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre))) 
				throw new IndicadorInexsistenteException("Indicador no cargado");
 
		}
}
