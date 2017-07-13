package condiciones;

import comparadores.IComparador;
import criterios.Criterio;
import repositorios.RepositorioIndicadores;

public class CondicionPrioridad {
	
	private Criterio criterio;
	private int peso;
	private RepositorioIndicadores repoIndicadores;
	private IComparador comparador;
	
	public CondicionPrioridad(Criterio criterio, int peso, RepositorioIndicadores repoIndicadores,
			IComparador comparador) {
		this.criterio = criterio;
		this.peso = peso;
		this.repoIndicadores = repoIndicadores;
		this.comparador = comparador;
	}
	public Criterio getCriterio() {
		return criterio;
	}
	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public RepositorioIndicadores getRepoIndicadores() {
		return repoIndicadores;
	}
	public void setRepoIndicadores(RepositorioIndicadores repoIndicadores) {
		this.repoIndicadores = repoIndicadores;
	}
	public IComparador getComparador() {
		return comparador;
	}
	public void setComparador(IComparador comparador) {
		this.comparador = comparador;
	}

	
}
