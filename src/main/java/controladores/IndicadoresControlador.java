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
import model.Cuenta;
import model.Indicador;
import model.Usuario;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioIndicadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorCuenta;
import validadores.ValidadorIndicadores;

public class IndicadoresControlador implements WithGlobalEntityManager, TransactionalOps {
	
	
	public Void crear(Request request, Response response) {
		RepositorioIndicadores repo = new RepositorioIndicadores();
	    String nombre = request.queryParams("nombre");
	    String formula = request.queryParams("formula");
	    String usuario = request.session().attribute("usuario");
	    
	    if(new ValidadorIndicadores().estaCargado(nombre, formula, repo)){
	    	// El indicador ya está cargado...
	    	response.redirect("/indicadores/error");
	    	return null;
	    }
	    
	    withTransaction(() -> {
	      repo.persistir(new Indicador(nombre, formula, usuario));
	    });
	
	    response.redirect("/indicadores");
	    return null;
	  }

	public ModelAndView listar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    List<Indicador> indicadores;
	    String filtroNombre = request.queryParams("filtroNombre");
	    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty()) {
	      indicadores = findIndicadores(usuario);
	    } else {
	      indicadores = findIndicadores(usuario).stream()
	    		  		.filter(indicador -> indicador.getNombre().equals(filtroNombre))
	    		  		.collect(Collectors.toList());
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
	
	public ModelAndView addCuenta(Request request, Response response) {
		RepositorioEmpresas repo = new RepositorioEmpresas();
		String nombre_empresa = request.queryParams("nombreEmpresa");
		System.out.println("Nombre: " + nombre_empresa);
		Empresa empresa = repo.getEmpresa(nombre_empresa);
		System.out.println("Nombre EMPRESA: " + empresa.getNombre());
		List<Cuenta> empresa_cuentas = empresa.getCuentas();
	    String nombre_cuenta = request.queryParams("nombreCuenta");
	    String periodo = request.queryParams("periodoCuenta");
	    String valor = request.queryParams("valorCuenta");
	    
	    ValidadorCuenta validador = new ValidadorCuenta();
	    System.out.println("Nombre: " + nombre_cuenta + " ," + periodo + ", " + valor);
	    if(validador.validarQueNoEsteYaCargarda(nombre_cuenta, periodo, empresa_cuentas)){
	    	// El nombre ya está en uso...
	    	System.out.println("BBBBB");
	    	response.redirect("/empresas/:id/error");
	    	return null;
	    }
	    System.out.println("CCCCCC");
	    withTransaction(() -> {
	    	empresa.agregarCuenta(new Cuenta(nombre_cuenta, periodo, valor));
	    	repo.persistir(empresa);
	    });
	    System.out.println("DDDDDD");
	    response.redirect("/empresas");
	    return null;
	  }
}

