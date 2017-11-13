package controladores;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorUsuario;

public class HomeControlador {
	
	public ModelAndView mostrar(Request request, Response response){
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: mostrar_home(request, response);
	}	
	
	public ModelAndView mostrar_home(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("usuario", usuario);
		return new ModelAndView(viewModel, "home.hbs");
	}
}
