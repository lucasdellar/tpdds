package criterios;

import java.util.Comparator;
import java.util.List;

import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import domain.Indicador;
import domain.Valor;
import edu.emory.mathcs.backport.java.util.Collections;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class CriterioPorIndicador extends Criterio {

	private String periodo;
	private Indicador indicador;
	
	public CriterioPorIndicador(Indicador indicador, String periodo) {
		super(indicador);
		this.periodo = periodo;
	}
	
	private Double obtenerValorIndicador(Empresa unaEmpresa, Condicion unaCondicion){
		return indicador.aplicarIndicador(periodo, unaEmpresa, unaCondicion.getRepoIndicadores());
	}

	@Override
	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
		return unaCondicion.getComparador().
				comparar(obtenerValorIndicador(unaEmpresa, unaCondicion), unaCondicion.getValue());
	}

	@Override
	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion) {

		return unaCondicion.getComparador().
				comparar(obtenerValorIndicador(unaEmpresa, unaCondicion), 
						 obtenerValorIndicador(otraEmpresa, unaCondicion));
	}

}
