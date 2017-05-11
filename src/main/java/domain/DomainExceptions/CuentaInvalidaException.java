package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class CuentaInvalidaException extends UserException{
	
	public CuentaInvalidaException(String message) {
		super(message);
	}
}
