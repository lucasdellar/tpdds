package domain;

import empresas.Empresa;
import repositorios.Repositorio;
import repositorios.RepositorioIndicadores;

public class ValorIndicador implements Valor{

	Indicador indicador;

	public ValorIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	@Override
	public Double calcular(String periodo, Empresa unaEmpresa, RepositorioIndicadores repo) {
		return indicador.aplicarIndicador(periodo, unaEmpresa, repo);
	}

}
