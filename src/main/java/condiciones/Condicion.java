package condiciones;

import java.util.List;
import comparadores.Comparador;
import criterios.Criterio;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public abstract class Condicion {
	
	private Criterio criterio;
	private RepositorioIndicadores repoIndicadores;
	private Comparador comparador;
	
	public Condicion(RepositorioIndicadores indicadores, Comparador comparador){
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

	public Comparador getComparador() {
		return comparador;
	}

	void setComparador(Comparador comparador) {
		this.comparador = comparador;
	}
	
}
