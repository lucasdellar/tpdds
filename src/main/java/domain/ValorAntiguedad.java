package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import empresas.Empresa;
import repositorios.Repositorio;
import repositorios.RepositorioIndicadores;

public class ValorAntiguedad implements Valor{
	
	@Override
	public Double calcular(String periodo, Empresa unaEmpresa, RepositorioIndicadores repo) {
		
		List<Integer> periodos = unaEmpresa.getCuentas().stream().map(unaCuenta -> Integer.parseInt(unaCuenta.getPeriodo()))
								.collect(Collectors.toList());
	
		int minIndex = periodos.indexOf(java.util.Collections.min(periodos));
		int maxIndex = periodos.indexOf(java.util.Collections.max(periodos));
		
		Double antiguedad = (periodos.get(maxIndex) - periodos.get(minIndex)) * 1.0;
		return antiguedad;
	}

}
