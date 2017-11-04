package controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import empresas.Empresa;
import model.Indicador;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioIndicadores;
import repositorios.RepositorioMetodologias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorIndicadores;

public class MetodologiasControlador  implements WithGlobalEntityManager, TransactionalOps {
	
	ValidadorIndicadores validador;
	RepositorioIndicadores repo;
	String lastUser;
	List<Empresa> empresasSeleccionadas;
	List<Empresa> empresasNoSeleccionadas;
	
	public MetodologiasControlador() {
		empresasSeleccionadas = new ArrayList<Empresa>();
		empresasNoSeleccionadas = new ArrayList<Empresa>();
	}
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "metodologias-error.hbs");
	  }
	public ModelAndView seleccionarEmpresas(Request request, Response response) {

	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	
	    
    	String metodologia = request.params(":metodologia");
	    HashMap<String, Object> viewModel = new HashMap<>();

	    
	    viewModel.put("empresasNoSeleccionadas", empresasNoSeleccionadas);
	    viewModel.put("empresasSeleccionadas", empresasSeleccionadas);
	    viewModel.put("metodologia", metodologia);
	    return new ModelAndView(viewModel, "aplicar-indicador.hbs");
	  }
	
	
	public ModelAndView listar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}

    	RepositorioMetodologias repo = new RepositorioMetodologias();
    	List<Empresa> empresas = new RepositorioEmpresas().getLista();
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("metodologias", repo.getLista());
	    viewModel.put("empresas", empresas);
	    empresasNoSeleccionadas = new RepositorioEmpresas().getLista();
	    return new ModelAndView(viewModel, "metodologias.hbs");
	  }
	
	public ModelAndView agregar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	String empresa = request.queryParams("empresa");
    	System.out.println("---------->" + empresa);
    	RepositorioMetodologias repo = new RepositorioMetodologias();
    	List<Empresa> empresas = new RepositorioEmpresas().getLista();
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(viewModel, "prueba.hbs");
	  }
	
}