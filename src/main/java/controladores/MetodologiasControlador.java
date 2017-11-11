package controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import empresas.Empresa;
import empresas.EmpresaRankeada;
import model.Metodologia;
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
		empresasSeleccionadas = new RepositorioEmpresas().getLista();
		empresasNoSeleccionadas = new ArrayList<Empresa>();
	}
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "metodologias-error.hbs");
	  }
	
	public ModelAndView listar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	empresasNoSeleccionadas = new RepositorioEmpresas().getLista();
    	empresasSeleccionadas = new ArrayList<>();
    	RepositorioMetodologias repo = new RepositorioMetodologias();
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("metodologias", repo.getLista());
	    return new ModelAndView(viewModel, "seleccionar-metodologia.hbs");
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
	    return new ModelAndView(viewModel, "seleccionar-empresas.hbs");
	  }
	
	public ModelAndView agregarEmpresa(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	String metodologia = request.params("metodologia");
    	String empresa = request.queryParams("empresa");
    	int index = 0;
    	
    	if(empresa.isEmpty())
    		response.redirect("metodologias/" + metodologia);
    	
    	for(Empresa unaEmpresa : empresasNoSeleccionadas){
    		if(unaEmpresa.getNombre().equals(empresa)){
    			index = empresasNoSeleccionadas.indexOf(unaEmpresa);
    			empresasNoSeleccionadas.remove(unaEmpresa);
    			empresasSeleccionadas.add(unaEmpresa);
    			break;
    		}
    	}
    	
	    response.redirect("metodologias/" + metodologia);
	    return null;
	  }
	
	public ModelAndView aplicar(Request request, Response response) {
    	String usuario = request.session().attribute("usuario");
    	if (usuario == null) {
    		response.redirect("/login");
    		return null;
    	}
    	String nombre = request.params("metodologia");
    	Metodologia metodologia = new RepositorioMetodologias().getLista().stream().
    								  filter(unaMetodologia -> unaMetodologia.getNombre().equals(nombre))
    								  .collect(Collectors.toList())
    								  .get(0);
    	List<EmpresaRankeada> resultado = metodologia.aplicar(empresasSeleccionadas);
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresas", resultado);
	    return new ModelAndView(viewModel, "mostrar-resultado.hbs");
	  }
	
}