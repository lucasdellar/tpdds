package criterios;

import comparadores.Comparador;
import domain.Valor;
import empresas.Empresa;

public class PorValor extends Criterio {
	
	public PorValor(Valor valor) {
		super(valor);
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, Comparador unComparador) {
		return unComparador.comparar(calcular(unaEmpresa), unValor);
	}

	@Override
	public double calcular(Empresa unaEmpresa){
		return valor.calcular(unaEmpresa);
	}
}
