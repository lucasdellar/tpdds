package criterios;

import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import condiciones.CondicionPrioritaria;
import domain.Indicador;
import empresas.Empresa;

public abstract class Criterio {
	
	private Indicador indicador;
	
	public Criterio(Indicador indicador){
		this.indicador = indicador;
	}
	
	public abstract Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion);
	
	public abstract Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion);

	public Indicador getIndicador() {
		return indicador;
	}

	void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	
}
