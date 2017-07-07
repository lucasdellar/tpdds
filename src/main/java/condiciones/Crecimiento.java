package condiciones;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comparadores.IComparador;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

public class Crecimiento extends Condicion
{

	public Crecimiento(RepositorioIndicadores indicadores, IComparador comparador) {
		super(indicadores, comparador);
	}

	@Override
	public ArrayList<EmpresaRankeada> aplicarCondicion(ArrayList<EmpresaRankeada> empresas) {
		
		ArrayList<EmpresaRankeada> empresasAInvertir = new ArrayList();
		
		List<EmpresaRankeada> unasEmpresas = empresas.stream()
				.filter(x -> getCriterio().aplicarCriterio(	x, this)).collect(Collectors.toList());
		
		return new ArrayList<EmpresaRankeada>(unasEmpresas);
	}

}
