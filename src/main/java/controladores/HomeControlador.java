package controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeControlador {

	  public ModelAndView mostrar(Request request, Response response) {
		    return new ModelAndView(null, "home.hbs");
		  }
}
