package criterios;

import java.util.List;

import javax.persistence.Entity;

import comparadores.Comparador;
import empresas.Empresa;
import model.Valor;

@Entity
public class Sumatoria extends Criterio{

	private Sumatoria(){}
	
	public Sumatoria(Valor valor) {
		super(valor);
	}

	@Override
	public double calcular(Empresa unaEmpresa) {
		List<String> periodos = obtenerPeriodos(unaEmpresa.getCuentas());
		return periodos.stream().mapToDouble(unPeriodo -> actualizarPeriodo(unaEmpresa, unPeriodo)).sum();
	}
	
	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, Comparador unComparador) {
		
		double sumatoria = 0;
		
		sumatoria = calcular(unaEmpresa);
		
		return unComparador.comparar(sumatoria, unValor);
	}
	
}

