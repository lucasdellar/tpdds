package criterios;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import comparadores.IComparador;
import domain.Cuenta;
import domain.Valor;
import empresas.Empresa;

public abstract class Criterio {
	
	protected Valor valor;
	
	public Criterio(Valor valor) {
		this.valor = valor;
	}
	
	public Double actualizarPeriodo(Empresa unaEmpresa, String periodo){
		valor.setPeriodo(periodo);
		return valor.calcular(unaEmpresa);
	}
	
	public List<String> obtenerPeriodos(List<Cuenta> cuentas){
		String formula = valor.getIndicador().getFormula();
		List<Cuenta> cuentasValidas = cuentas.stream().filter(unaCuenta -> formula.contains(unaCuenta.getNombre())).collect(Collectors.toList());
		
		List<String> periodos = cuentasValidas.stream().map(unaCuenta -> unaCuenta.getPeriodo()).distinct().collect(Collectors.toList());

		return periodos;
	}
	
	public abstract double calcular(Empresa unaEmpresa);
	
	public abstract Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador unComparador);
	
}
