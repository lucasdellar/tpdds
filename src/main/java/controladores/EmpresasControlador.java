package controladores;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import empresas.Empresa;
import repositorios.RepositorioEmpresas;
import scala.collection.parallel.ParIterableLike.Foreach;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadores.ValidadorEmpresa;

public class EmpresasControlador implements WithGlobalEntityManager, TransactionalOps {
	
	public ModelAndView nueva(Request request, Response response) {
	    return new ModelAndView(null, "empresa-nueva.hbs");
	  }
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "empresa-error.hbs");
	  }
	
	public Void crear(Request request, Response response) {
		RepositorioEmpresas repo = new RepositorioEmpresas();
	    String nombre = request.queryParams("nuevaEmpresa");
	    
	    if(new ValidadorEmpresa().validarNombre(nombre, repo)){
	    	// El nombre ya está en uso...
	    	response.redirect("/empresas/error");
	    	return null;
	    }
	    
	    withTransaction(() -> {
	      repo.agregar(new Empresa(nombre));
	    });
	
	    response.redirect("/empresas");
	    return null;
	  }

	public ModelAndView listar(Request request, Response response) {
	    List<Empresa> empresas;

	    String filtroNombre = request.queryParams("filtroNombre");
	    if (Objects.isNull(filtroNombre) || filtroNombre.isEmpty()) {
	      empresas = new RepositorioEmpresas().getLista();
	    } else {
	      empresas = new RepositorioEmpresas().getLista().stream().
	    		  filter(empresa -> empresa.getNombre().equals(filtroNombre))
	    		  .collect(Collectors.toList());
	    }

	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresas", empresas);
	    viewModel.put("filtroNombre", filtroNombre);

	    return new ModelAndView(viewModel, "empresas.hbs");
	  }

	public ModelAndView mostrar(Request request, Response response) {
	    String nombre = request.params(":id");

	    System.out.println(nombre);
	    Empresa empresa = new RepositorioEmpresas().getLista().stream()
	    		.filter(unaEmpresa -> unaEmpresa.getNombre().equals(nombre)).collect(Collectors.toList()).get(0);
	    
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresa", empresa);
	    return new ModelAndView(empresa, "empresa.hbs");
	  }
}
