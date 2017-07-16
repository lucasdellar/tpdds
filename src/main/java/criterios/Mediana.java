package criterios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import domain.Indicador;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Mediana  extends Criterio{

	
	public Mediana(Indicador indicador) {
		super(indicador);
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
		
		List<Double> indicadoresAplicados = unaEmpresa.getCuentas().stream()
				.map( x -> 
				getIndicador().aplicarIndicador(x.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()))
				.collect(Collectors.toList());
			
		indicadoresAplicados.sort(new Comparator<Double>(){
			@Override
			public int compare(Double n1, Double n2) {
				return n1.compareTo(n2);
			}
		});
		
		int size = indicadoresAplicados.size();
		return  unaCondicion.getComparador().comparar( esPar(size) ? medianaPar(indicadoresAplicados, size) : 
				medianaImpar(indicadoresAplicados), unaCondicion.getValue());
	}

	private Double medianaImpar(List<Double> indicadoresAplicados) {
		return indicadoresAplicados.get(indicadoresAplicados.size() / 2);
	}

	private double medianaPar(List<Double> indicadoresAplicados, int size) {
		return (indicadoresAplicados.get(size / 2)  + indicadoresAplicados.get( (size / 2) - 1)) / 2;
	}

	public Boolean esPar(int numero){
		return numero % 2 == 0;
	}
	
}
