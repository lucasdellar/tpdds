package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class OperacionInvalidaException extends UserException {
	
	public OperacionInvalidaException(String message){
		super(message);
	}
}
