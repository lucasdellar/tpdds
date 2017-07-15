package condiciones;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comparadores.IComparador;
import criterios.Criterio;
import domain.Indicador;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import repositorios.Repositorio;
import repositorios.RepositorioIndicadores;

public class CondicionTaxativa extends Condicion {
	
	public CondicionTaxativa(RepositorioIndicadores indicadores, IComparador comparador, int value) {
		/* Para el caso en el que la condicion requiera de un valor */
		super(indicadores, comparador);
		this.value = value;
	}
		
	public CondicionTaxativa(RepositorioIndicadores indicadores, IComparador comparador) {
		/* En ciertos casos la condicion Taxativa puede no requerir de valor alguno. 
		 * Por ejemplo cuando se quiere aplica el criterio Crecimiento.
		 */
		super(indicadores, comparador);
	}

	@Override
	public List<EmpresaRankeada> aplicar(List<EmpresaRankeada> empresas) {
		List<EmpresaRankeada> empresas_a_invertir = empresas.stream()
				.filter(empresa -> getCriterio().aplicar(empresa, this))
				.collect(Collectors.toList());

		return new ArrayList<EmpresaRankeada>(empresas_a_invertir); 
	}
	
}
