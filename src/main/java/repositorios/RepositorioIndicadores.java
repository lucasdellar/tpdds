package repositorios;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import domain.DomainExceptions.IndicadorInexsistenteException;
import model.Indicador;


public class RepositorioIndicadores extends Repositorio<Indicador> {
	
	public RepositorioIndicadores(List<Indicador> indicadores){
		this.setLista(indicadores);
	}

	public RepositorioIndicadores(){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Indicador> criteria = builder.createQuery(Indicador.class);
		criteria.from(Indicador.class);
		this.setLista(manager.createQuery(criteria).getResultList());
	}
	
	public Indicador indicadorDesdeString(String indicadorString) {
		for(Indicador indicador : this.getLista()){
			if(indicador.getNombre().equals(indicadorString))
				return indicador;
		}
		return this.getLista().get(0);
		//throw new IndicadorInexsistenteException("Se busco un indicador inexistente.");
	}

}
