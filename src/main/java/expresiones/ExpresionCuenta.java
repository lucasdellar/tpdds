package expresiones;

import empresas.Empresa;

import java.util.stream.Collectors;

import domain.Cuenta;
import domain.DomainExceptions.IndicadorInvalidoException;

public class ExpresionCuenta implements Expresion{

	String nombreCuenta;
	
	public ExpresionCuenta(String unaCuenta){
		this.nombreCuenta= unaCuenta;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo){
		try {
			String valor = unaEmpresa.getCuentas().stream()
									 .filter(unaCuenta -> esLaCuenta(unaCuenta, unPeriodo))
									 .collect(Collectors.toList())
									 .get(0).getValor();
			return Double.parseDouble(valor);
		} catch (IndexOutOfBoundsException e) {
			throw new IndicadorInvalidoException("Se esta utilizando un indicador sobre una empresa con datos insuficientes.");
		}
	}
	
	Boolean esLaCuenta(Cuenta unaCuenta, String unPeriodo){
		return unaCuenta.getNombre().equals(nombreCuenta) && unaCuenta.getPeriodo().equals(unPeriodo);
	}
	
}
