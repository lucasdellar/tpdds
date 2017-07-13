package condiciones;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import comparadores.IComparador;
import criterios.Criterio;
import domain.Indicador;
import edu.emory.mathcs.backport.java.util.Collections;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public class ComparaEmpresasPorIndicador extends Condicion {


	private String periodo;
	private Indicador indicador;
	
	public ComparaEmpresasPorIndicador(RepositorioIndicadores repoIndicadores, IComparador comparador, Indicador indicador, String periodo) {
		super(repoIndicadores, comparador);
		this.periodo = periodo;
		this.indicador = indicador;
	}

	@Override
	public List<EmpresaRankeada> aplicar(List<EmpresaRankeada> empresas) {
		
		empresas.sort(new Comparator<EmpresaRankeada>(){

			@Override
			public int compare(EmpresaRankeada emp1, EmpresaRankeada emp2) {
				return indicador.aplicarIndicador(periodo, emp1, getRepoIndicadores()).
						compareTo(indicador.aplicarIndicador(periodo, emp2, getRepoIndicadores()));
			}
		});
		Collections.reverse(empresas);
		aumentarPeso(empresas);
		return empresas;
	}

	private void aumentarPeso(List<EmpresaRankeada> empresas) {
		for(EmpresaRankeada unaEmp : empresas){
			unaEmp.aumentarRanking(getPeso() * (empresas.size() - empresas.indexOf(unaEmp)));    // Manera propia de calcularle el peso
		}
		
	}

}
