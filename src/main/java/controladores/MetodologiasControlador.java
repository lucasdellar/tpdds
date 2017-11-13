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
import validadores.ValidadorUsuario;

public class MetodologiasControlador  implements WithGlobalEntityManager, TransactionalOps {
	
	ValidadorIndicadores validador;
	RepositorioIndicadores repo;
	String lastUser;
	List<Empresa> empresasSeleccionadas;
	List<Empresa> empresasNoSeleccionadas;
	RepositorioMetodologias repo_metodologias; 
	
	public MetodologiasControlador() {
		empresasSeleccionadas = new RepositorioEmpresas().getLista();
		empresasNoSeleccionadas = new ArrayList<Empresa>();
	}
	
	public ModelAndView error(Request request, Response response) {
	    return new ModelAndView(null, "metodologias-error.hbs");
	  }
	
	// -------------------------------------- Validación de Usuario -------------------------------------- //
	
	public ModelAndView listar(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarLista(request, response);
	  }
	
	public ModelAndView seleccionarEmpresas(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarSeleccionEmpresas(request, response);
	  }
	
	public ModelAndView agregarEmpresa(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarAgregarEmpresa(request, response);
	  }
	
	public ModelAndView aplicar(Request request, Response response) {
		return ValidadorUsuario.ChequearUsuarioLogeado(request, response) ? null
				: this.mostrarAplicar(request, response);
	  }
	
	// -------------------------------------- Métodos principales -------------------------------------- //
	
	public ModelAndView mostrarLista(Request request, Response response) {
		RepositorioEmpresas repo = new RepositorioEmpresas();
		repo.traerTodas();
    	empresasNoSeleccionadas = repo.getLista();
    	empresasSeleccionadas = new ArrayList<>();
    	repo_metodologias = new RepositorioMetodologias();
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("metodologias", repo_metodologias.getLista());
	    return new ModelAndView(viewModel, "seleccionar-metodologia.hbs");
	  }
	
	public ModelAndView mostrarSeleccionEmpresas(Request request, Response response) {    	
    	String metodologia = request.params(":metodologia");
    	
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresasNoSeleccionadas", empresasNoSeleccionadas);
	    viewModel.put("empresasSeleccionadas", empresasSeleccionadas);
	    viewModel.put("metodologia", metodologia);
	    return new ModelAndView(viewModel, "seleccionar-empresas.hbs");
	  }
	
	public ModelAndView mostrarAgregarEmpresa(Request request, Response response) {
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
	
	public ModelAndView mostrarAplicar(Request request, Response response) {
    	String nombre = request.params("metodologia");
    	Metodologia metodologia = repo_metodologias.find(nombre);
    	List<EmpresaRankeada> resultado = metodologia.aplicar(empresasSeleccionadas);
	    HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("empresas", resultado);
	    return new ModelAndView(viewModel, "mostrar-resultado.hbs");
	  }
	
}