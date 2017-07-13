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
	
	int value;
	
	public CondicionTaxativa(RepositorioIndicadores indicadores, IComparador comparador, int value) {
		super(indicadores, comparador);
		this.value = value;
	}

	@Override
	public Boolean aplicar(Empresa empresa) {
		return this.getCriterio().aplicarCriterio(empresa, this);
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
