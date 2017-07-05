package domain;

import java.util.ArrayList;

public class CondicionTaxativa {

	ArrayList<Indicador> indicadores;
	
	public CondicionTaxativa(ArrayList<Indicador> indicadores){
		this.indicadores = indicadores;
	}
	
	public boolean cumpleCondicion(Empresa unaEmpresa){
		return true;
	}
	
}
 

