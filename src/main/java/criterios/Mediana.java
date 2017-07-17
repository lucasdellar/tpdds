package criterios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import domain.Indicador;
import domain.Valor;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Mediana  extends Criterio{

	public Mediana(Valor valor) {
		super(valor);
	}
	
	private Double calcular(Empresa unaEmpresa, Condicion unaCondicion){
		List<Double> indicadoresAplicados = unaEmpresa.getCuentas().stream()
				.map( cuenta -> 
				valor.calcular(cuenta.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()))
				.collect(Collectors.toList());
			
		indicadoresAplicados.sort(new Comparator<Double>(){
			@Override
			public int compare(Double n1, Double n2) {
				return n1.compareTo(n2);
			}
		});
		
		int size = indicadoresAplicados.size();
		return esPar(size) ? medianaPar(indicadoresAplicados, size) : medianaImpar(indicadoresAplicados);
	}

	@Override
	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
		return  unaCondicion.getComparador().comparar(calcular(unaEmpresa, unaCondicion), 
				unaCondicion.getValue()); 
	}

	@Override
	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion) {
		return unaCondicion.getComparador().comparar(calcular(unaEmpresa, unaCondicion), 
				calcular(otraEmpresa, unaCondicion));
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
