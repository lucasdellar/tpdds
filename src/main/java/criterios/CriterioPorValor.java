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

public class CriterioPorValor extends Criterio {

	private String periodo;
	
	public CriterioPorValor(Valor valor, String periodo) {
		super(valor);
		this.periodo = periodo;
	}

	@Override
	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
		return unaCondicion.getComparador().
				comparar(valor.calcular(periodo, unaEmpresa, unaCondicion.getRepoIndicadores()), 
						unaCondicion.getValor());
	}

	@Override
	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion) {

		return unaCondicion.getComparador().
				comparar(valor.calcular(periodo, unaEmpresa, unaCondicion.getRepoIndicadores()), 
						valor.calcular(periodo, otraEmpresa, unaCondicion.getRepoIndicadores()));
	}

}
