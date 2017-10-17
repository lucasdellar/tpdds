package main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import controladores.EmpresasControlador;
import controladores.HomeControlador;
import controladores.LoginControlador;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Rutas {

  public static void main(String[] args) {
	LoginControlador login = new LoginControlador();
    HomeControlador home = new HomeControlador();
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    EmpresasControlador empresas = new EmpresasControlador();

    port(8080);
    staticFileLocation("/public");

    get("/login", login::mostrar, engine);
    post("/login", login::login, engine);
    get("/", (request, response) -> {
      response.redirect("/login");
      return null;
    });
    get("/home", home::mostrar, engine);
    get("/empresas/nueva", empresas::nueva, engine);
    get("/empresas", empresas::listar, engine);
    get("/empresas/error", empresas::error, engine);
    post("/empresas", empresas::crear);
    get("/empresas/:id", empresas::mostrar, engine);
    post("/empresas/:id", empresas::addCuenta, engine);
  }

}
