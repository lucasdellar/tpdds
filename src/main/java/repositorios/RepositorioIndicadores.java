package repositorios;

import javax.persistence.Entity;
import javax.persistence.Id;

import domain.Indicador;
import domain.DomainExceptions.IndicadorInexsistenteException;


public class RepositorioIndicadores extends Repositorio<Indicador> {

	public RepositorioIndicadores(){
		this.setLista(manager.createQuery("SELECT i FROM Indicador i").getResultList());
	}
	
	public Indicador indicadorDesdeString(String indicadorString) {
		for(Indicador indicador : this.getLista()){
			if(indicador.getNombre().equals(indicadorString))
				return indicador;
		}
		throw new IndicadorInexsistenteException("Se busco un indicador inexistente.");
	}

}
