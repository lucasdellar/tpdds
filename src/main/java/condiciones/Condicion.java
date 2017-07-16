package condiciones;

import java.util.ArrayList;
import java.util.List;

import comparadores.IComparador;
import criterios.Criterio;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public abstract class Condicion {
	
	private Criterio criterio;
	private RepositorioIndicadores repoIndicadores;
	private IComparador comparador;
	
	public Condicion(RepositorioIndicadores indicadores, IComparador comparador){
		this.setRepoIndicadores(indicadores);
		this.comparador = comparador;
	}
	
	public abstract List<EmpresaRankeada> aplicar(List<EmpresaRankeada> empresas);

	public RepositorioIndicadores getRepoIndicadores() {
		return repoIndicadores;
	}

	void setRepoIndicadores(RepositorioIndicadores repoIndicadores) {
		this.repoIndicadores = repoIndicadores;
	}

	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	public IComparador getComparador() {
		return comparador;
	}

	void setComparador(IComparador comparador) {
		this.comparador = comparador;
	}
	
}
