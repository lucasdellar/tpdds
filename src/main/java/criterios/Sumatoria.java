package criterios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import condiciones.Condicion;
import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;

public class Sumatoria extends Criterio{

	
	public Sumatoria(Indicador indicador) {
		super(indicador);
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, Condicion unaCondicion) {
		
		double sumatoria = 0;
		
		sumatoria = unaEmpresa.getCuentas().stream().mapToDouble( x -> this.getIndicador().aplicarIndicador(x.getPeriodo(), 
					unaEmpresa, unaCondicion.getRepoIndicadores())).sum();
		
		return unaCondicion.getComparador().comparar(sumatoria, unaCondicion.getValue());
	}
}
