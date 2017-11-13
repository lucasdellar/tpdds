package controladores;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import model.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginControlador {
	
	  public ModelAndView mostrar(Request request, Response response) {
		  return new ModelAndView(null, "login.hbs");
	  }
	  
	  public ModelAndView error(Request request, Response response) {
		  return new ModelAndView(null, "login-error.hbs");
	  }
	  
	  public ModelAndView logout(Request request, Response response) {
		  request.session().removeAttribute("usuario");
		  response.redirect("/login");
		  return null;
	  }
	  
	  public ModelAndView login(Request request, Response response) {
		  String usuario_nombre = request.queryParams("usuario");
		  String password = request.queryParams("password");

		  List<Usuario> queryResult = new RepositorioUsuarios().findUsuario(usuario_nombre);
		  
		  if(queryResult.isEmpty()){
			response.redirect("/error");
			return null;
		  }
		  if(!queryResult.get(0).getPassword().equals(password)){
			  response.redirect("/error");
			  return null;
		  }
		  
		  request.session().attribute("usuario", usuario_nombre);
		  response.redirect("/home");
		  return null;
	  }
}
