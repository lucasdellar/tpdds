package repositorios;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import domain.DomainExceptions.IndicadorInexsistenteException;
import model.Indicador;


public class RepositorioIndicadores extends Repositorio<Indicador> {
	
	public RepositorioIndicadores(String usuario){
		this.setLista(this.findIndicadores(usuario));
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
	}
	
	private List<Indicador> findIndicadores(String usuario) {
		  EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		  CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		  CriteriaQuery<Indicador> criteriaQuery = criteriaBuilder.createQuery(Indicador.class);
		  Root<Indicador> root = criteriaQuery.from(Indicador.class);
		  criteriaQuery.select(root);
		  ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
		  criteriaQuery.where(criteriaBuilder.equal(root.get("usuario"), params));
		  TypedQuery<Indicador> query = entityManager.createQuery(criteriaQuery);
		  query.setParameter(params, usuario);
		  return query.getResultList();
	}
	
	public List<Indicador> findIndicador(String nombre){
		return this.getLista().stream().filter(x -> x.getNombre()
	    		   .equals(nombre))
				   .collect(Collectors.toList());
	}

}
