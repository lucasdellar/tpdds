package repositorios;

import java.util.stream.Collectors;

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
	
	public Metodologia find(String nombre){
		return this.getLista().stream().
				  filter(unaMetodologia -> unaMetodologia.getNombre().equals(nombre))
				  .collect(Collectors.toList())
				  .get(0);
	}
	
}
