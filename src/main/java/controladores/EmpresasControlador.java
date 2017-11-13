package controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import empresas.Empresa;
import model.Cuenta;
import repositorios.RepositorioEmpresas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorCuenta;
import validadores.ValidadorEmpresa;
import validadores.ValidadorUsuario;

public class EmpresasControlador implements WithGlobalEntityManager, TransactionalOps {
	
	RepositorioEmpresas repoEmpresas;
	
	public EmpresasControlador(){
		repoEmpresas = new RepositorioEmpresas();
	}
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "empresa-error.hbs");
	}

	// -------------------------------------- Validación de Usuario -------------------------------------- //
	public ModelAndView cuenta_error(Request request, Response response){
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: mostrar_cuenta_error(request, response);
	}
	
	public ModelAndView mostrar(Request request, Response response){
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: mostrarEmpresa(request, response);
	}
	
	public ModelAndView listar(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: listarEmpresas(request, response);
	}
	
	// -------------------------------------- Métodos principales -------------------------------------- //
	
	public Void crear(Request request, Response response) {
	    String nombre = request.queryParams("nuevaEmpresa");
	    
	    if(new ValidadorEmpresa().validarNombre(nombre, repoEmpresas)){
	    	response.redirect("/empresas/error");
	    	return null;
	    } 
	    
    	repoEmpresas.agregar(new Empresa(nombre));
	    response.redirect("/empresas");
	    return null;
	  }
	
	public ModelAndView listarEmpresas(Request request, Response response){
	    List<Empresa> empresas;
	    String filtroNombre = request.queryParams("filtroNombre");
	    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty()){
	    	repoEmpresas.traerTodas();
	    	empresas = repoEmpresas.getLista();	    	
	    }
	    else
	      empresas = repoEmpresas.getEmpresas(filtroNombre);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresas", empresas);
	    viewModel.put("filtroNombre", filtroNombre);

	    return new ModelAndView(viewModel, "empresas.hbs");
	}

	public ModelAndView mostrarEmpresa(Request request, Response response) {
	    String nombre = request.params(":id");
	    Empresa empresa = repoEmpresas.getEmpresas(nombre).get(0);
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(empresa, "empresa.hbs");
	  }
	
	public ModelAndView addCuenta(Request request, Response response) {
		String nombre_empresa = request.queryParams("nombreEmpresa");
		Empresa empresa = repoEmpresas.getEmpresas(nombre_empresa).get(0);
		List<Cuenta> empresa_cuentas = empresa.getCuentas();
	    String nombre_cuenta = request.queryParams("nombreCuenta");
	    String periodo = request.queryParams("periodoCuenta");
	    String valor = request.queryParams("valorCuenta");
	    
	    ValidadorCuenta validador = new ValidadorCuenta();
	    if(validador.validarQueNoEsteYaCargarda(nombre_cuenta, periodo, empresa_cuentas)){
	    	response.redirect("/empresas/" + nombre_empresa + "/error");
	    	return null;
	    }
	    withTransaction(() -> {
	    	empresa.agregarCuenta(new Cuenta(nombre_cuenta, periodo, valor));
	    	repoEmpresas.persistir(empresa);
	    });
	    response.redirect("/empresas/" + nombre_empresa);
	    return null;
	  }
	
	public ModelAndView mostrar_cuenta_error(Request request, Response response) {
	    String empresa = request.params(":id");
    	HashMap<String, Object> viewModel = new HashMap<>();
 	    viewModel.put("empresa", empresa);
    	
    	return new ModelAndView(viewModel, "cuenta-error.hbs");
	  }
}
