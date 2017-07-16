package criterios;

import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import condiciones.CondicionPrioritaria;
import domain.Indicador;
import empresas.Empresa;

public class Sumatoria extends Criterio{

	
	public Sumatoria(Indicador indicador) {
		super(indicador);
	}

	public double sumar(Empresa unaEmpresa, Condicion unaCondicion) {
		return unaEmpresa.getCuentas().stream().mapToDouble( x -> this.getIndicador().aplicarIndicador
				(x.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores())).sum();
	}
	
	@Override
	public Boolean aplicar(Empresa unaEmpresa, CondicionTaxativa condicion_taxativa) {
		
		double sumatoria = 0;
		
		sumatoria = sumar(unaEmpresa, condicion_taxativa);
		
		return condicion_taxativa.getComparador().comparar(sumatoria, condicion_taxativa.getValue());
	}
	
	@Override
	public Boolean aplicar(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria condicion_prioritaria) {
		
		double sumatoria1, sumatoria2 = 0;
		
		sumatoria1 = sumar(unaEmpresa, condicion_prioritaria);
		sumatoria2 = sumar(otraEmpresa, condicion_prioritaria);
		
		return condicion_prioritaria.getComparador().comparar(sumatoria1, sumatoria2);
	}
	
}
