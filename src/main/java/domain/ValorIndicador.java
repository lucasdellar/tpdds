package domain;

import empresas.Empresa;
import repositorios.Repositorio;
import repositorios.RepositorioIndicadores;

public class ValorIndicador implements Valor{

	Indicador indicador;
	String periodo;
	RepositorioIndicadores repo;

	public ValorIndicador(Indicador indicador, String periodo, RepositorioIndicadores repo) {
		this.indicador = indicador;
	}

	@Override
	public Double calcular(Empresa unaEmpresa) {
		return indicador.aplicarIndicador(this.periodo, unaEmpresa, this.repo);
	}

}
