package Condiciones;

import java.util.ArrayList;
import java.util.Comparator;

import comparadores.IComparador;
import criterios.Criterio;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public class ComparaEmpresasPorIndicador extends Condicion {


	private String periodo;
	
	public ComparaEmpresasPorIndicador(RepositorioIndicadores repoIndicadores, IComparador comparador ,Criterio criterio, String periodo) {
		super(repoIndicadores, comparador);
		this.setCriterio(criterio);
		this.periodo = periodo;
	}

	@Override
	public ArrayList<EmpresaRankeada> aplicarCondicion(ArrayList<EmpresaRankeada> empresas) {
		
		empresas.sort(new Comparator<EmpresaRankeada>(){

			@Override
			public int compare(EmpresaRankeada emp1, EmpresaRankeada emp2) {
				return getCriterio().getIndicador().aplicarIndicador(periodo, emp1, getRepoIndicadores()).
						compareTo(getCriterio().getIndicador().aplicarIndicador(periodo, emp2, getRepoIndicadores()));
			}
		});
		aumentarPeso(empresas);
		return empresas;
	}

	private void aumentarPeso(ArrayList<EmpresaRankeada> empresas) {
		for(EmpresaRankeada unaEmp : empresas){
			unaEmp.aumentarRanking(peso * (empresas.size() - empresas.indexOf(unaEmp)));    // Manera propia de calcularle el peso
		}
		
	}

}
