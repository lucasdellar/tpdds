package repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import model.Usuario;


public class RepositorioUsuarios extends Repositorio<Usuario> {
	
	public RepositorioUsuarios(){}
	
	public List<Usuario> findUsuario(String usuario_nombre){
	  EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
	  CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	  CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
	  Root<Usuario> root = criteriaQuery.from(Usuario.class);
	  criteriaQuery.select(root);
	  ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
	  criteriaQuery.where(criteriaBuilder.equal(root.get("usuario"), params));
	  TypedQuery<Usuario> query = entityManager.createQuery(criteriaQuery);
	  query.setParameter(params, usuario_nombre);
	  return query.getResultList();
	 }
	
	
}
