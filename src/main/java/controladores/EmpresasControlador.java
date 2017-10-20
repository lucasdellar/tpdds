package controladores;

import java.util.ArrayList;
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

public class EmpresasControlador implements WithGlobalEntityManager, TransactionalOps {
	
	RepositorioEmpresas repoEmpresas;
	
	public EmpresasControlador(){
		repoEmpresas = new RepositorioEmpresas();
	}
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "empresa-error.hbs");
	  }
	
	public Void crear(Request request, Response response) {
	    String nombre = request.queryParams("nuevaEmpresa");
	    
	    if(new ValidadorEmpresa().validarNombre(nombre, repoEmpresas)){
	    	// El nombre ya est� en uso...
	    	response.redirect("/empresas/error");
	    	return null;
	    }
	    
    	repoEmpresas.agregar(new Empresa(nombre));
	
	    response.redirect("/empresas");
	    return null;
	  }

	public ModelAndView listar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    List<Empresa> empresas;
	    String filtroNombre = request.queryParams("filtroNombre");
	    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty()) {
	      empresas = repoEmpresas.getLista();
	    } else {
	      empresas = repoEmpresas.getLista().stream().
	    		  filter(empresa -> empresa.getNombre().equals(filtroNombre))
	    		  .collect(Collectors.toList());
	    }
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresas", empresas);
	    viewModel.put("filtroNombre", filtroNombre);

	    return new ModelAndView(viewModel, "empresas.hbs");
	  }

	public ModelAndView mostrar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
	    String nombre = request.params(":id");

	    Empresa empresa = repoEmpresas.getEmpresa(nombre);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(empresa, "empresa.hbs");
	  }
	
	public ModelAndView addCuenta(Request request, Response response) {
		String nombre_empresa = request.queryParams("nombreEmpresa");
		System.out.println("Nombre: " + nombre_empresa);
		Empresa empresa = repoEmpresas.getEmpresa(nombre_empresa);
		System.out.println("Nombre EMPRESA: " + empresa.getNombre());
		List<Cuenta> empresa_cuentas = empresa.getCuentas();
	    String nombre_cuenta = request.queryParams("nombreCuenta");
	    String periodo = request.queryParams("periodoCuenta");
	    String valor = request.queryParams("valorCuenta");
	    
	    ValidadorCuenta validador = new ValidadorCuenta();
	    System.out.println("Nombre: " + nombre_cuenta + " ," + periodo + ", " + valor);
	    if(validador.validarQueNoEsteYaCargarda(nombre_cuenta, periodo, empresa_cuentas)){
	    	// El nombre ya est� en uso...
	    	System.out.println("BBBBB");
	    	response.redirect("/empresas/:id/error");
	    	return null;
	    }
	    System.out.println("CCCCCC");
	    withTransaction(() -> {
	    	empresa.agregarCuenta(new Cuenta(nombre_cuenta, periodo, valor));
	    	repoEmpresas.persistir(empresa);
	    });
	    System.out.println("DDDDDD");
	    response.redirect("/empresas");
	    return null;
	  }
}
