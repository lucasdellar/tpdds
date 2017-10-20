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
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorIndicadores;

public class IndicadoresControlador implements WithGlobalEntityManager, TransactionalOps {
	
	ValidadorIndicadores validador;
	RepositorioIndicadores repo;
	String lastUser;
	
	public IndicadoresControlador(){
		validador = new ValidadorIndicadores();
		lastUser = "";
	}
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "indicador-error.hbs");
	  }
	
	public Void crear(Request request, Response response) {
		String nombre_usuario = request.session().attribute("usuario");
		repo = new RepositorioIndicadores(findIndicadores(nombre_usuario));
	    String nombre = request.queryParams("nombre");
	    String formula = request.queryParams("formula");
	    String usuario = request.session().attribute("usuario");
	    
	    if(!validador.esValido(nombre, formula, repo)){
	    	// El indicador ya est� cargado...
	    	response.redirect("/indicadores/error");
	    	return null;
	    }
	    
	    repo.agregar(new Indicador(nombre, formula, usuario));
	    /*
	    withTransaction(() -> {
	      repo.persistir(new Indicador(nombre, formula, usuario));
	    });*/
	
	    response.redirect("/indicadores");
	    return null;
	  }

	public ModelAndView listar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	if(!lastUser.equals(usuario)){
    	    lastUser = usuario;
    		repo = new RepositorioIndicadores(findIndicadores(usuario));
    	}
    	List<Indicador> indicadores;
	    String filtroNombre = request.queryParams("filtroNombre");
	    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty()) {
	    	indicadores = repo.getLista(); 
	    	//indicadores = findIndicadores(usuario);
	    } else {
	    	indicadores = repo.getLista().stream()
    		  		.filter(indicador -> indicador.getNombre().equals(filtroNombre))
    		  		.collect(Collectors.toList());
	       /* indicadores = findIndicadores(usuario).stream()
	    		  		.filter(indicador -> indicador.getNombre().equals(filtroNombre))
	    		  		.collect(Collectors.toList());*/
	    }

	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicadores", indicadores);
	    viewModel.put("filtroNombre", filtroNombre);

	    return new ModelAndView(viewModel, "indicadores.hbs");
	  }

	private List<Indicador> findIndicadores(String usuario) {
		  EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		  CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		  CriteriaQuery<Indicador> criteriaQuery = criteriaBuilder.createQuery(Indicador.class);
		  Root<Indicador> root = criteriaQuery.from(Indicador.class);
		  criteriaQuery.select(root);
		  ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
		  criteriaQuery.where(criteriaBuilder.equal(root.get("usuario"), params));
		  TypedQuery<Indicador> query = entityManager.createQuery(criteriaQuery);
		  query.setParameter(params, usuario);
		  return query.getResultList();
	}

	public ModelAndView mostrar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    String nombre = request.params(":id");

	    System.out.println(nombre);
	    Empresa empresa = new RepositorioEmpresas().getEmpresa(nombre);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(empresa, "empresa.hbs");
	  }
	
}

