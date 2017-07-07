package condiciones;

import java.util.ArrayList;

import comparadores.IComparador;
import criterios.Criterio;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public abstract class Condicion {
	
	private Criterio criterio;
	int peso;
	private RepositorioIndicadores repoIndicadores;
	private IComparador comparador;
	
	public Condicion(RepositorioIndicadores indicadores,  IComparador comparador){
		this(indicadores, 0, comparador);
	}
	
	public Condicion(RepositorioIndicadores indicadores, int peso, IComparador comparador){
		this.setRepoIndicadores(indicadores);
		this.peso = peso;
		this.comparador = comparador;
	}
	
	public abstract ArrayList<EmpresaRankeada> aplicarCondicion(ArrayList<EmpresaRankeada> empresas);

	public RepositorioIndicadores getRepoIndicadores() {
		return repoIndicadores;
	}

	void setRepoIndicadores(RepositorioIndicadores repoIndicadores) {
		this.repoIndicadores = repoIndicadores;
	}

	Criterio getCriterio() {
		return criterio;
	}

	void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	public IComparador getComparador() {
		return comparador;
	}

	void setComparador(IComparador comparador) {
		this.comparador = comparador;
	}
	
}
