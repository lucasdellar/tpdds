package domain;

import empresas.Empresa;
import repositorios.RepositorioIndicadores;

public class ValorIndicador implements Valor{
	
	String indicador;
	String periodo;
	RepositorioIndicadores repo;

	public ValorIndicador(String indicador, RepositorioIndicadores repo) {
		this.indicador = indicador;
		this.repo = repo;
	}

	public Double calcular(Empresa unaEmpresa) {
		return repo.indicadorDesdeString(indicador).aplicarIndicador(this.periodo, unaEmpresa, this.repo);
	}
	
	public void setPeriodo(String unPeriodo){
		this.periodo = unPeriodo;
	}

}
