package condiciones;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import domain.Indicador;
import empresas.EmpresaRankeada;

public class DependeDeValor extends Condicion {
	
	Criterio criterio;
	int valor;
	int anios;
	IComparador miComparador;
	
	public DependeDeValor(ArrayList<Indicador> indicadores, IComparador miComparador, int valor) {
		super(indicadores);
		this.valor = valor;
		this.miComparador = miComparador;
		anios = 0;
	}
	
	public DependeDeValor(ArrayList<Indicador> indicadores, IComparador miComparador, int valor, int anios) {
		this(indicadores, miComparador, valor);
		this.anios = anios;
	}

	@Override
	public ArrayList<EmpresaRankeada> aplicarCondicion(ArrayList<EmpresaRankeada> empresas) {
		return (ArrayList<EmpresaRankeada>) empresas.stream().filter(x -> criterio.aplicarCriterio(x, miComparador, anios, valor));
	}


}
