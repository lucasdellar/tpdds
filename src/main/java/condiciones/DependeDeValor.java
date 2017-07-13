package condiciones;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comparadores.IComparador;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public class DependeDeValor extends Condicion {
	
	private int valor;
	
	public DependeDeValor(RepositorioIndicadores repoIndicadores, IComparador miComparador, int valor) {
		super(repoIndicadores, miComparador);
		this.setValor(valor);
	}
	
	@Override
	public ArrayList<EmpresaRankeada> aplicar(List<EmpresaRankeada> empresas) {
		List<EmpresaRankeada> unasEmpresas = empresas.stream()
				.filter(x -> getCriterio().aplicarCriterio(x, this)).collect(Collectors.toList());
		
		return new ArrayList<EmpresaRankeada>(unasEmpresas); 
	}

	public int getValor() {
		return valor;
	}

	void setValor(int valor) {
		this.valor = valor;
	}


}
