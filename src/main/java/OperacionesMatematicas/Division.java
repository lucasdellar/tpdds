package OperacionesMatematicas;

import domain.DomainExceptions.OperacionInvalidaException;

public class Division implements Operador{

	public double calcular(double operando1, double operando2) {
		if(operando2 != 0)
			return operando1 / operando2; 
		else 
			throw new OperacionInvalidaException("Se intento dividir por 0.");
	}
}
