package criterios;

import comparadores.IComparador;
import domain.Valor;
import empresas.Empresa;

public class Promedio extends Criterio{

	public Promedio(Valor valor) {
		super(valor);
	}
	
	@Override
	public double calcular(Empresa unaEmpresa) {
		double sumatoria = 0;
		sumatoria = unaEmpresa.getCuentas().stream().mapToDouble(unaCuenta -> actualizarPeriodo(unaEmpresa, unaCuenta)).sum();
		
		return sumatoria / unaEmpresa.getCuentas().size();
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador comparador) {
		double promedio = calcular(unaEmpresa);
		
		return  comparador.comparar(promedio, unValor);
	}
}
