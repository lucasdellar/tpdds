package condiciones;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import comparadores.Comparador;
import empresas.EmpresaRankeada;
import repositorios.RepositorioIndicadores;

@Entity
@DiscriminatorValue(value="CondicionTaxativa")
public class CondicionTaxativa extends Condicion {
	
	@Column(name = "valor")
	private int valor;
	
	private CondicionTaxativa(){}
	
	public CondicionTaxativa(RepositorioIndicadores indicadores, Comparador comparador, int valor) {
		/* Para el caso en el que la condicion requiera de un valor 
		 */
		
		super(indicadores, comparador);
		this.valor = valor;
	}
		
	public CondicionTaxativa(RepositorioIndicadores indicadores, Comparador comparador) {
		/* En ciertos casos la condicion Taxativa puede no requerir de valor alguno. 
		 * Por ejemplo cuando se quiere aplica el criterio Crecimiento.
		 */
		
		super(indicadores, comparador);
	}


	public List<EmpresaRankeada> aplicar(List<EmpresaRankeada> empresas) {
		List<EmpresaRankeada> empresas_a_invertir = empresas.stream()
			.filter(empresaRankeada -> getCriterio().aplicar(empresaRankeada.getEmpresa(), getValor(), getComparador())).collect(Collectors.toList());
		return empresas_a_invertir; 
	}

	public int getValor() {
		return valor;
	}

	public void setValue(int value) {
		this.valor = value;
	}
	
}
