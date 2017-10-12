package repositorios;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import empresas.Empresa;
import model.ConversorFormatoArchivo;
import model.Metodologia;

public class RepositorioMetodologias extends Repositorio<Metodologia> {
	
	public RepositorioMetodologias(){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Metodologia> criteria = builder.createQuery(Metodologia.class);
		criteria.from(Metodologia.class);
		this.setLista(manager.createQuery(criteria).getResultList());
	}
	
}
