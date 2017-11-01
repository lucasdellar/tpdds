package controladores;

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
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "metodologias-error.hbs");
	  }
	public ModelAndView aplicar(Request request, Response response) {

	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	
	    String nombreIndicador = request.params(":indicador");
	    String nombreEmpresa = request.params(":empresa");
	    String periodo = request.params(":periodo");

	    Indicador indicador = repo.indicadorDesdeString(nombreIndicador);
	    
	    System.out.println("empresa " + nombreEmpresa);
	    
	    System.out.println("indicador " + nombreIndicador);
	    
	    Empresa empresa = new RepositorioEmpresas().getLista().stream().filter(x -> x.getNombre()
	    		.equals(nombreEmpresa)).collect(Collectors.toList()).get(0);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();

	    String resultado = indicador.aplicarIndicador(periodo, empresa, repo).toString();
	    
	    viewModel.put("indicador", indicador.getNombre());
	    viewModel.put("empresa", empresa.getNombre());
	    viewModel.put("resultado", resultado);
	    return new ModelAndView(viewModel, "aplicar-indicador.hbs");
	  }
	
	
	public ModelAndView listar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}

    	RepositorioMetodologias repo = new RepositorioMetodologias();
    	
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("metodologias", repo.getLista());

	    return new ModelAndView(viewModel, "metologias.hbs");
	  }
	
}