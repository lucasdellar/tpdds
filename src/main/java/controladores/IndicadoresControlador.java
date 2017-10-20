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
	
	public ModelAndView seleccionarPeriodo(Request request, Response response) {

	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
        System.out.println("111111111");
	    
	    String nombreIndicador = request.params(":indicador");
	    System.out.println("2222222222");
	    String nombreEmpresa = request.params(":empresa");

	    System.out.println("nombre indicador " + nombreIndicador);
	    
	    Indicador indicador = repo.indicadorDesdeString(nombreIndicador);
	    
	    System.out.println("nombre indicador 2 " + indicador.getNombre());
	    Empresa empresa = new RepositorioEmpresas().getLista().stream().filter(x -> x.getNombre()
	    		.equals(nombreEmpresa)).collect(Collectors.toList()).get(0);
	    List<String> posiblesPeriodos = empresa.getCuentas().stream().map( x -> x.getPeriodo()).collect(Collectors.toList());
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    
	    viewModel.put("indicador", indicador.getNombre());
	    viewModel.put("formula", indicador.getFormula());
	    viewModel.put("empresa", nombreEmpresa);
	    viewModel.put("periodos", posiblesPeriodos);
	    
	    return new ModelAndView(viewModel, "seleccionar-periodo.hbs");
	  }
	
	public ModelAndView seleccionarEmpresa(Request request, Response response) {

	   	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    String nombre = request.params(":indicador");
	    System.out.println("666666666666");
	    
	    System.out.println("nombre indicador en SE : " + nombre);
	    
	    Indicador indicador = repo.indicadorDesdeString(nombre);
	    List<Empresa> empresas = new RepositorioEmpresas().getLista();
	    HashMap<String, Object> viewModel = new HashMap<>();
	    System.out.println("333333333");
	    viewModel.put("indicador", indicador.getNombre());
	    System.out.println("444444444");
	    viewModel.put("formula", indicador.getFormula());
	    viewModel.put("empresas", empresas);
	    System.out.println("555555555");
	    return new ModelAndView(viewModel, "seleccionar-empresa.hbs");
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

