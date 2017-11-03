package controladores;

import java.util.Comparator;
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

import domain.DomainExceptions.IndicadorInvalidoException;
import empresas.Empresa;
import model.Indicador;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioIndicadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorIndicadores;
import validadores.ValidadorUsuario;

public class IndicadoresControlador implements WithGlobalEntityManager, TransactionalOps {
	
	ValidadorIndicadores validador;
	RepositorioIndicadores repo;
	String lastUser;
	
	public IndicadoresControlador(){
		validador = new ValidadorIndicadores();
		lastUser = "";
	}
	
	public ModelAndView error(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: new ModelAndView(null, "indicador-error.hbs");
	  }
	
	public ModelAndView error_aplicar(Request request, Response response) {
	   	String usuario = request.session().attribute("usuario");    	
		if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    String nombreEmpresa = request.params(":empresa");
	    String nombreIndicador = request.params(":indicador");
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicador", nombreIndicador);
	    viewModel.put("empresa", nombreEmpresa);
	    return new ModelAndView(viewModel, "indicador-aplicar-error.hbs");
	  }
	
	public Void crear(Request request, Response response) {
		String nombre_usuario = request.session().attribute("usuario");
		repo = new RepositorioIndicadores(findIndicadores(nombre_usuario));
	    String nombre = request.queryParams("nombre");
	    String formula = request.queryParams("formula");
	    String usuario = request.session().attribute("usuario");
	    
	    if(!validador.esValido(nombre, formula, repo)){
	    	response.redirect("/indicadores/error");
	    	return null;
	    }
	    
	    repo.agregar(new Indicador(nombre, formula, usuario));
	
	    response.redirect("/indicadores");
	    return null;
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
	    
	    Empresa empresa = new RepositorioEmpresas().getLista().stream().filter(x -> x.getNombre()
	    		.equals(nombreEmpresa)).collect(Collectors.toList()).get(0);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();

	    String resultado = indicador.aplicarIndicador(periodo, empresa, repo).toString();
	    
	    viewModel.put("indicador", indicador.getNombre());
	    viewModel.put("empresa", empresa.getNombre());
	    viewModel.put("resultado", resultado);
	    return new ModelAndView(viewModel, "aplicar-indicador.hbs");
	  }
	
	public ModelAndView mostrarResultadoIndicador(Request request, Response response) {
	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	
	    String nombreEmpresa = request.params(":empresa");
	    String periodo = request.params(":periodo");
	    String nombreIndicador = request.params(":indicador");
	    Double resultado;
	    Empresa empresa = new RepositorioEmpresas().getLista().stream().filter(x -> x.getNombre()
									    		   .equals(nombreEmpresa))
	    										   .collect(Collectors.toList()).get(0);
	    Indicador indicador = repo.getLista().stream().filter(x -> x.getNombre()
	    		   .equals(nombreIndicador))
				   .collect(Collectors.toList()).get(0);
	    
	    try{
	    	resultado = indicador.aplicarIndicador(periodo, empresa, repo);
	    }catch(IndicadorInvalidoException e){
    		response.redirect("/empresas/"+nombreEmpresa+"/indicadores/"+nombreIndicador+"/"+periodo+"/error");
    		return null;
	    }
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    
	    viewModel.put("resultado", resultado);
	    viewModel.put("indicador", indicador.getNombre());
	    viewModel.put("empresa", nombreEmpresa);
	    return new ModelAndView(viewModel, "mostrarResultadoIndicador.hbs");
	  }
	
	public ModelAndView seleccionarPeriodo(Request request, Response response) {

	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    String nombreEmpresa = request.params(":empresa");
	    String nombreIndicador = request.params(":indicador");
	    Empresa empresa = new RepositorioEmpresas().getLista().stream().filter(x -> x.getNombre()
									    		   .equals(nombreEmpresa))
	    										   .collect(Collectors.toList()).get(0);
	    List<String> posiblesPeriodos = empresa.getCuentas().stream()
	    									   .map( x -> x.getPeriodo())
	    									   .collect(Collectors.toList());
	    posiblesPeriodos.sort(Comparator.comparing(periodo -> Integer.valueOf(periodo)));
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", nombreEmpresa);
	    viewModel.put("periodos", posiblesPeriodos);
	    viewModel.put("indicador", nombreIndicador);
	    return new ModelAndView(viewModel, "seleccionar-periodo.hbs");
	  }
	
	public ModelAndView seleccionarIndicador(Request request, Response response) {

	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    String empresa = request.params(":empresa");
	    repo = new RepositorioIndicadores(findIndicadores(usuario));
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicadores", repo.getLista());
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(viewModel, "seleccionar-indicador.hbs");
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

