package criterios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.helpers.ISO8601DateFormat;

import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import domain.Indicador;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Mediana  extends Criterio{

	
	public Mediana(Indicador indicador) {
		super(indicador);
	}

	@Override
	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
		
		List<Double> indicadoresAplicados = unaEmpresa.getCuentas().stream()
				.map( cuenta -> 
				getIndicador().aplicarIndicador(cuenta.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()))
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

	@Override
	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion) {
		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
	}
	
}
