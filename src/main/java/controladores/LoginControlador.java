package controladores;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import model.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginControlador {
	
	  public ModelAndView mostrar(Request request, Response response) {
		  return new ModelAndView(null, "login.hbs");
	  }
	  
	  public ModelAndView login(Request request, Response response) {
		  String usuario_nombre = request.queryParams("usuario");
		  String password = request.queryParams("password");
		  EntityManager entityManager = PerThreadEntityManagers.getEntityManager();

//		  CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		    CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
//		    Root<Usuario> root = criteriaQuery.from(Usuario.class);
//		    criteriaQuery.select(root);
//
//		    ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
//		    criteriaQuery.where(criteriaBuilder.equal(root.get("usuario"), params));
//
//		    TypedQuery<Usuario> query = entityManager.createQuery(criteriaQuery);
//		    query.setParameter(params, usuario_nombre);
//
//		    List<Usuario> queryResult = query.getResultList();
		
		  if(false){
			response.redirect("/login/error");
			return null;
		  }
		
		  response.redirect("/home");
		  return null;
	  }
}
