package criterios;

import comparadores.IComparador;
import domain.Cuenta;
import domain.Valor;
import empresas.Empresa;

public abstract class Criterio {
	
	protected Valor valor;
	
	public Criterio(Valor valor) {
		this.valor = valor;
	}
	
	public Double actualizarPeriodo(Empresa unaEmpresa, Cuenta unaCuenta){
		valor.setPeriodo(unaCuenta.getPeriodo());
		return valor.calcular(unaEmpresa);
	}
	
	public abstract double calcular(Empresa unaEmpresa);
	
	public abstract Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador unComparador);
	
}
