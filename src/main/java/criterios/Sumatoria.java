package criterios;

import comparadores.IComparador;
import domain.Valor;
import empresas.Empresa;

public class Sumatoria extends Criterio{

	public Sumatoria(Valor valor) {
		super(valor);
	}

	@Override
	public double calcular(Empresa unaEmpresa) {
		return unaEmpresa.getCuentas().stream().mapToDouble(unaCuenta -> actualizarPeriodo(unaEmpresa, unaCuenta)).sum();
	}
	
	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador unComparador) {
		
		double sumatoria = 0;
		
		sumatoria = calcular(unaEmpresa);
		
		return unComparador.comparar(sumatoria, unValor);
	}
	
}

