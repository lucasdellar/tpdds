package condiciones;

import java.util.Comparator;
import java.util.List;

import comparadores.IComparador;
import criterios.Criterio;
import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;
import edu.emory.mathcs.backport.java.util.Collections;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;


public class CondicionPrioritaria extends Condicion {
	
	private int peso;
	
	public CondicionPrioritaria(RepositorioIndicadores indicadores, IComparador comparador, int peso) {
		super(indicadores, comparador);
		this.peso = peso;
	}
	
	@Override
	public List<EmpresaRankeada> aplicar(List<EmpresaRankeada> empresas) {
		
		this.ordenarSegunCriterio(empresas);
		this.aumentarRankings(empresas);
		
		return empresas;
	}

	private void aumentarRankings(List<EmpresaRankeada> empresas) {
		for(EmpresaRankeada empresa : empresas){
			/* 
			 * De la siguiente manera calculamos el peso.
			 */
			empresa.aumentarRanking(getPeso() * (empresas.size() - empresas.indexOf(empresa)));
		}
	}
	
	private List<EmpresaRankeada> ordenarSegunCriterio(List<EmpresaRankeada> empresas) {

		empresas.sort(new Comparator<EmpresaRankeada>(){
			
			@Override
			public int compare(EmpresaRankeada unaEmpresa, EmpresaRankeada otraEmpresa) {
				if(getCriterio().aplicar(unaEmpresa, otraEmpresa, CondicionPrioritaria.this))
					return 1;
				else 
					return -1;
			}
		});
		Collections.reverse(empresas);
		this.aumentarRankings(empresas);
		return empresas;
	}
	
	public int getPeso() {
		return peso;
	}
	
	public void setPeso(int peso) {
		this.peso = peso;
	}
}