package criterios;

import Condiciones.Condicion;
import domain.Indicador;
import empresas.Empresa;

public abstract class Criterio {
	
	private Indicador indicador;
	
	public Criterio(Indicador indicador){
		this.setIndicador(indicador);
	}
	
	public abstract Boolean aplicarCriterio(Empresa unaEmpresa, Condicion unaCondicion);

	public Indicador getIndicador() {
		return indicador;
	}

	void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	
}
