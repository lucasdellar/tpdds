package domain;

import empresas.Empresa;
import repositorios.Repositorio;
import repositorios.RepositorioIndicadores;

public interface Valor {

	public Double calcular(Empresa unaEmpresa);
}
