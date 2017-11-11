package validadores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ValidadorUsuario {

	public static boolean ChequearUsuarioLogeado(Request request, Response response){
    	if (request.session().attribute("usuario") == null) {
    		response.redirect("/login");
    		return true;
    	}
    	return false;
	}
	
}
