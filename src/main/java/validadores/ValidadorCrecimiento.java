package validadores;

import domain.DomainExceptions.IntervaloInvalidoException;

public class ValidadorCrecimiento {

	public void validarIntervalo(String inicioPeriodo, String finPeriodo) {
		int inicio = Integer.parseInt(inicioPeriodo);
		int fin = Integer.parseInt(finPeriodo);

		if(inicio < 0 || fin < 0 || inicio > fin)
			throw new IntervaloInvalidoException("El intervalo ingresado no es valido. El inicio debe ser menor que el"
					+ "fin y ambos valores deben ser positivos. ");
		
	}

}
