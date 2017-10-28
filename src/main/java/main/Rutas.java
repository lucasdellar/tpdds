package main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import controladores.EmpresasControlador;
import controladores.HomeControlador;
import controladores.IndicadoresControlador;
import controladores.MetodologiasControlador;
import controladores.LoginControlador;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Rutas {

  public static void main(String[] args) {
	LoginControlador login = new LoginControlador();
    HomeControlador home = new HomeControlador();
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    EmpresasControlador empresas = new EmpresasControlador();
    IndicadoresControlador indicadores = new IndicadoresControlador();
    MetodologiasControlador metodologias = new MetodologiasControlador();

    port(8080);
    staticFileLocation("/public");

    get("/login", login::mostrar, engine);
    post("/login", login::login, engine);
    get("/error", login::error, engine);
    get("/", (request, response) -> {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    	} else {
    		response.redirect("/home");
    	}
    	return null;
    });
    get("/logout", login::logout, engine);
    
    get("/home", home::mostrar, engine);
    get("/empresas", empresas::listar, engine);
    get("/empresas/error", empresas::error, engine);
    post("/empresas", empresas::crear);
    get("/empresas/:id", empresas::mostrar, engine);
    post("/empresas/:id", empresas::addCuenta, engine); 
    get("/indicadores", indicadores::listar, engine);
    post("/indicadores", indicadores::crear);
    get("/empresas/:empresa/indicadores", indicadores::seleccionarIndicador, engine);
    get("/empresas/:empresa/indicadores/:indicador", indicadores::seleccionarPeriodo, engine);
    get("/empresas/:empresa/indicadores/:indicador/:periodo", indicadores::mostrarResultadoIndicador, engine);
    get("/indicadores/error", indicadores::error, engine);
  
    get("/metodologias", metodologias::listar, engine);
    get("/metodologias/:metodologia", metodologias::aplicar, engine);
    
  }

}
