package main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import controladores.EmpresasControlador;
import controladores.HomeControlador;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Rutas {

  public static void main(String[] args) {
    HomeControlador home = new HomeControlador();
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    EmpresasControlador empresas = new EmpresasControlador();

    port(8080);
    staticFileLocation("/public");

    get("/", home::mostrar, engine);
    get("/index.html", (request, response) -> {
      response.redirect("/");
      return null;
    });
    get("/empresas/nueva", empresas::nueva, engine);
    get("/empresas", empresas::listar, engine);
    get("/empresas/error", empresas::error, engine);
    post("/empresas", empresas::crear);
    get("/empresas/:id", empresas::mostrar, engine);
  }

}
