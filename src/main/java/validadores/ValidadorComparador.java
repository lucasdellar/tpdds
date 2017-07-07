package validadores;

import domain.DomainExceptions.ComparadorInvalidoException;

public class ValidadorComparador {

	public void validarString(String mayorMenor) {
		if(!(mayorMenor.equals("MAYOR") || mayorMenor.equals("MENOR")))
				throw new ComparadorInvalidoException("Debe ingresar MENOR o MAYOR como comparador.");
	}
}
