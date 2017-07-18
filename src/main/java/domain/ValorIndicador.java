package domain;

import empresas.Empresa;
import repositorios.RepositorioIndicadores;

public class ValorIndicador implements Valor{
	
	Indicador indicador;
	String periodo;
	RepositorioIndicadores repo;

	public ValorIndicador(Indicador indicador, RepositorioIndicadores repo) {
		this.indicador = indicador;
		this.repo = repo;
	}

	@Override
	public Double calcular(Empresa unaEmpresa) {
		return indicador.aplicarIndicador(this.periodo, unaEmpresa, this.repo);
	}
	
	@Override
	public void setPeriodo(String unPeriodo){
		this.periodo = unPeriodo;
	}

}
