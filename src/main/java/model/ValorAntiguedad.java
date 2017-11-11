package model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import empresas.Empresa;

@Entity
public class ValorAntiguedad extends Valor{
	
	private ValorAntiguedad(){}
	
	public Double calcular(Empresa unaEmpresa) {
		
		List<Integer> periodos = unaEmpresa.getCuentas().stream().map(unaCuenta -> Integer.parseInt(unaCuenta.getPeriodo()))
								.collect(Collectors.toList());
	
		int minIndex = periodos.indexOf(java.util.Collections.min(periodos));
		int maxIndex = periodos.indexOf(java.util.Collections.max(periodos));
		
		Double antiguedad = (periodos.get(maxIndex) - periodos.get(minIndex)) * 1.0;
		return antiguedad;
	}

	public void setPeriodo(String periodo) {
		
	}
	
	public Indicador getIndicador() {
		return null;
	}

}
