package ui.ViewModels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import domain.Metodologia;
import domain.DomainExceptions.NoSeCargaronEmpresasException;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioMetodologias;

@Observable
public class EvaluarMetodologiaViewModel {
	
	
	private Metodologia metodologiaSeleccionada;
	private List <EmpresaRankeada> empresasRankeadas;
	private List <Metodologia> metodologias;
	private List <Empresa> empresas;
	private List <Empresa> empresasAEvaluar;
	private Empresa empresaSeleccionada;
	private Empresa empresaSeleccionadaAEvaluar;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	
	public EvaluarMetodologiaViewModel(RepositorioEmpresas repositorioEmpresas, RepositorioMetodologias repositorioMetodologias){
		empresasRankeadas = new ArrayList<EmpresaRankeada>();
		empresas = repositorioEmpresas.getLista();
		setMetodologias(repositorioMetodologias.getLista());
		empresasAEvaluar = new ArrayList<Empresa>();
	}
	
	public List <EmpresaRankeada> getEmpresasRankeadas() {
		return empresasRankeadas;
	}
	public void setEmpresasRankeadas(List <EmpresaRankeada> empresasRankeadas) {
		this.empresasRankeadas = empresasRankeadas;
	}
	public List <Empresa> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List <Empresa> empresas) {
		this.empresas = empresas;
	}

	public List <Empresa> getEmpresasAEvaluar() {
		return empresasAEvaluar;
	}
	public void setEmpresasAEvaluar(List <Empresa> empresasAEvaluar) {
		this.empresasAEvaluar = empresasAEvaluar;
	}
	
	
	public void agregarEmpresaAEvaluar()
	{
		if(!empresasAEvaluar.contains(getEmpresaSeleccionada()))
			empresasAEvaluar.add(getEmpresaSeleccionada());
	}
	
	
	public void removerEmpresaAEvaluar()
	{
		if(empresasAEvaluar.contains(getEmpresaSeleccionada())){
			empresasAEvaluar.remove(empresasAEvaluar.indexOf(getEmpresaSeleccionada()));
		}
	}
	
	public void evaluarEmpresas()
	{
		if(empresasAEvaluar.isEmpty() || metodologiaSeleccionada == null) 
			throw new NoSeCargaronEmpresasException("Debe elegir una metodologia y aniadir empresas para poder evaluarlas.");
		
		getMetodologiaSeleccionada().aplicarMetodologia(empresasAEvaluar);
	}

	public List <Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List <Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}

	public Metodologia getMetodologiaSeleccionada() {
		return metodologiaSeleccionada;
	}

	public void setMetodologiaSeleccionada(Metodologia metodologiaSeleccionada) {
		this.metodologiaSeleccionada = metodologiaSeleccionada;
	}

	public Empresa getEmpresaSeleccionadaAEvaluar() {
		return empresaSeleccionadaAEvaluar;
	}

	public void setEmpresaSeleccionadaAEvaluar(Empresa empresaSeleccionadaAEvaluar) {
		this.empresaSeleccionadaAEvaluar = empresaSeleccionadaAEvaluar;
	}
	
	
	
	
	
}
