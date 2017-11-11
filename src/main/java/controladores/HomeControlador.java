package controladores;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class HomeControlador {

	  public ModelAndView mostrar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("usuario", usuario);
		return new ModelAndView(viewModel, "home.hbs");
	}
}
