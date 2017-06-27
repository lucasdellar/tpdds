package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.Empresa;
import domain.Indicador;
import repositorios.Repositorio;
import repositorios.RepositorioIndicadores;
import scala.collection.generic.BitOperations.Int;

@Observable
public class EvaluarEmpresaViewModel {
	
	private String periodo;
	private Indicador indicadorSeleccionado;
	private Empresa empresaSeleccionada;
	private RepositorioIndicadores repositorioIndicadores;
	private String resultado;
	
	public EvaluarEmpresaViewModel(Empresa unaEmpresa, RepositorioIndicadores unRepo){
		empresaSeleccionada		= unaEmpresa;
		setRepositorioIndicadores(unRepo);
		
	}
	
	public void resolverIndicador() {
		resultado = this.indicadorSeleccionado
				.aplicarIndicador( periodo, getEmpresaSeleccionada(), repositorioIndicadores).toString();
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Indicador getIndicadorSeleccionado() {
		return indicadorSeleccionado;
	}

	public void setIndicadorSeleccionado(Indicador indicadorSeleccionado) {
		this.indicadorSeleccionado = indicadorSeleccionado;
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}

	public RepositorioIndicadores getRepositorioIndicadores() {
		return repositorioIndicadores;
	}

	public void setRepositorioIndicadores(RepositorioIndicadores repositorioIndicadores) {
		this.repositorioIndicadores = repositorioIndicadores;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	

}
