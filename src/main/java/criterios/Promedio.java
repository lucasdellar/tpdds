package criterios;

import condiciones.Condicion;
import condiciones.DependeDeValor;
import domain.Indicador;
import empresas.Empresa;

public class Promedio extends Criterio{

	
	public Promedio(Indicador indicador) {
		super(indicador);
	}

	@Override
	public Boolean aplicarCriterio(Empresa unaEmpresa, Condicion unaCondicion) {
		
		double sumatoria = 0;
		
		sumatoria = unaEmpresa.getCuentas().stream().mapToDouble( x -> this.getIndicador().aplicarIndicador(x.getPeriodo(), 
					unaEmpresa, unaCondicion.getRepoIndicadores())).sum();
		
		return  unaCondicion.getComparador().comparar( sumatoria / unaEmpresa.getCuentas().size(), 
				((DependeDeValor) unaCondicion).getValor());
	}

}
