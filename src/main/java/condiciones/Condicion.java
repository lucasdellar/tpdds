package condiciones;

import java.util.ArrayList;

import domain.Indicador;
import empresas.EmpresaRankeada;

public abstract class Condicion {

	int peso;
	ArrayList<Indicador> indicadores;
	
	public Condicion(ArrayList<Indicador> indicadores){
		this(indicadores, 0);
	}
	
	public Condicion(ArrayList<Indicador> indicadores, int peso){
		this.indicadores = indicadores;
		this.peso = peso;
	}
	
	public abstract ArrayList<EmpresaRankeada> aplicarCondicion(ArrayList<EmpresaRankeada> empresas);
	
}
