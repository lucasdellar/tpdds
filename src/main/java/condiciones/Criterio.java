package condiciones;

import domain.Indicador;
import empresas.Empresa;

public abstract class Criterio {
	
	Indicador indicador;
	
	public Criterio(Indicador indicador){
		this.indicador = indicador;
	}
	
	public abstract Boolean aplicarCriterio(Empresa unaEmpresa, IComparador unComparador, int valor, int anios);
	
}
