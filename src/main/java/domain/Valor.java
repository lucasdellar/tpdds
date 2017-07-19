package domain;

import empresas.Empresa;

public interface Valor {

	public Double calcular(Empresa unaEmpresa);

	public void setPeriodo(String periodo);

}
