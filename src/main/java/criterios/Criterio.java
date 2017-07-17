package criterios;

import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import condiciones.CondicionPrioritaria;
import domain.Indicador;
import domain.Valor;
import empresas.Empresa;

public abstract class Criterio {
	
	protected Valor valor;
	
	public Criterio(Valor valor) {
		this.valor = valor;
	}

	public abstract Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion);
	
	public abstract Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion);
	
}
