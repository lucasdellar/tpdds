package criterios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import comparadores.IComparador;
import domain.Valor;
import empresas.Empresa;


public class Mediana  extends Criterio{

	public Mediana(Valor valor) {
		super(valor);
	}
	
	@Override
	public double calcular(Empresa unaEmpresa){
		List<Double> indicadoresAplicados = unaEmpresa.getCuentas().stream()
				.map(unaCuenta -> actualizarPeriodo(unaEmpresa, unaCuenta)).collect(Collectors.toList());
			
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
	public Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador unComparador) {
		return  unComparador.comparar(calcular(unaEmpresa), unValor); 
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
