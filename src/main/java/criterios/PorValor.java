package criterios;

import javax.persistence.Entity;

import comparadores.Comparador;
import empresas.Empresa;
import model.Valor;

@Entity
public class PorValor extends Criterio {
	
	private PorValor(){}
	
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
