package criterios;

import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import condiciones.CondicionPrioritaria;
import domain.Indicador;
import domain.Valor;
import empresas.Empresa;

public class Sumatoria extends Criterio{

	
	public Sumatoria(Valor valor) {
		super(valor);
	}

	public double sumar(Empresa unaEmpresa, Condicion unaCondicion) {
		return unaEmpresa.getCuentas().stream().mapToDouble(unaCuenta -> valor.calcular
				(unaCuenta.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores())).sum();
	}
	
	@Override
	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa condicion_taxativa) {
		
		double sumatoria = 0;
		
		sumatoria = sumar(unaEmpresa, condicion_taxativa);
		
		return condicion_taxativa.getComparador().comparar(sumatoria, condicion_taxativa.getValue());
	}
	
	@Override
	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria condicion_prioritaria) {
		
		double sumatoria1, sumatoria2 = 0;
		
		sumatoria1 = sumar(unaEmpresa, condicion_prioritaria);
		sumatoria2 = sumar(otraEmpresa, condicion_prioritaria);
		
		return condicion_prioritaria.getComparador().comparar(sumatoria1, sumatoria2);
	}
	
}
