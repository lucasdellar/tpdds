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
import model.IndicadorPorEmpresa;
import model.IndicadorPorEmpresaPK;
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
	
	// -------------------------------------- Validación de Usuario -------------------------------------- //
	
	public ModelAndView error(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: new ModelAndView(null, "indicador-error.hbs");
	  }
	
	public ModelAndView error_aplicar(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrar_error_aplicar(request, response);
	  }
	
	public ModelAndView aplicar(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrar_aplicar(request, response);
	  }
	
	public ModelAndView resultadoIndicador(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarResultadoIndicador(request, response);
	  }
	
	public ModelAndView seleccionarPeriodo(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarSeleccionarPeriodo(request, response);
	  }
	
	public ModelAndView seleccionarIndicador(Request request, Response response) {
			return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
					: this.mostrarSeleccionarIndicador(request, response);
		  }
	
	public ModelAndView listar(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarLista(request, response);
	  }
	
	// -------------------------------------- Métodos principales -------------------------------------- //
	
	public ModelAndView mostrar_error_aplicar(Request request, Response response) {
	    String nombreEmpresa = request.params(":empresa");
	    String nombreIndicador = request.params(":indicador");
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicador", nombreIndicador);
	    viewModel.put("empresa", nombreEmpresa);
	    return new ModelAndView(viewModel, "indicador-aplicar-error.hbs");
	  }
	
	public Void crear(Request request, Response response) {
		String nombre_usuario = request.session().attribute("usuario");
		repo = new RepositorioIndicadores(nombre_usuario);
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

	public ModelAndView mostrar_aplicar(Request request, Response response) {

	    String nombreIndicador = request.params(":indicador");
	    String nombreEmpresa = request.params(":empresa");
	    String periodo = request.params(":periodo");

	    Indicador indicador = repo.indicadorDesdeString(nombreIndicador);
	    Empresa empresa = new RepositorioEmpresas().getEmpresas(nombreEmpresa).get(0);
	    String resultado = indicador.aplicarIndicador(periodo, empresa, repo).toString();
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicador", indicador.getNombre());
	    viewModel.put("empresa", empresa.getNombre());
	    viewModel.put("resultado", resultado);
	    return new ModelAndView(viewModel, "aplicar-indicador.hbs");
	  }
	
	public ModelAndView mostrarResultadoIndicador(Request request, Response response) {
    	
	    String nombreEmpresa = request.params(":empresa");
	    String periodo = request.params(":periodo");
	    String nombreIndicador = request.params(":indicador");
	    Double resultado;
	    Empresa empresa = new RepositorioEmpresas().getEmpresas(nombreEmpresa).get(0);
	    Indicador indicador = repo.findIndicador(nombreIndicador).get(0);
	    
	    try{
	    	resultado = traerOCalcularIndicador(empresa, indicador, periodo);
	    	
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
	
	private double traerOCalcularIndicador(Empresa empresa, Indicador indicador, String periodo) {
		double resultado;
		try {
			IndicadorPorEmpresa unIndicadorPorEmpresa = entityManager().getReference(IndicadorPorEmpresa.class, new IndicadorPorEmpresaPK(empresa.getId(), indicador.getId(), periodo));
    		resultado = unIndicadorPorEmpresa.getResultado();
		}catch (Exception e){
			resultado = indicador.aplicarIndicador(periodo, empresa, repo);
    		IndicadorPorEmpresa otroIndicadorPorEmpresa = new IndicadorPorEmpresa(empresa.getId(), indicador.getId(), periodo, resultado);
    		withTransaction(() ->entityManager().persist(otroIndicadorPorEmpresa));	
		}
		
		return resultado;
	}

	public ModelAndView mostrarSeleccionarPeriodo(Request request, Response response) {
	    String nombreEmpresa = request.params(":empresa");
	    String nombreIndicador = request.params(":indicador");
	    Empresa empresa = new RepositorioEmpresas().getEmpresas(nombreEmpresa).get(0);
	    List<String> posiblesPeriodos = empresa.getPosibilesPeriodosOrdenados();
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", nombreEmpresa);
	    viewModel.put("periodos", posiblesPeriodos);
	    viewModel.put("indicador", nombreIndicador);
	    return new ModelAndView(viewModel, "seleccionar-periodo.hbs");
	  }
	
	public ModelAndView mostrarSeleccionarIndicador(Request request, Response response) {
		String usuario = request.session().attribute("usuario");
		String empresa = request.params(":empresa");
	    repo = new RepositorioIndicadores(usuario);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicadores", repo.getLista());
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(viewModel, "seleccionar-indicador.hbs");
	  }
	
	public ModelAndView mostrarLista(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if(!lastUser.equals(usuario)){
    	    lastUser = usuario;
    		repo = new RepositorioIndicadores(usuario);
    	}
    	
    	List<Indicador> indicadores;
	    String filtroNombre = request.queryParams("filtroNombre");
	    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty())
	    	indicadores = repo.getLista(); 
	    else
	    	indicadores = repo.findIndicador(filtroNombre);

	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("indicadores", indicadores);
	    viewModel.put("filtroNombre", filtroNombre);

	    return new ModelAndView(viewModel, "indicadores.hbs");
	  }
}

