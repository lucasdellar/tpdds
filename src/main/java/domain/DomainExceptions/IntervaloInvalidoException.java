package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class IntervaloInvalidoException extends UserException {
	
	public IntervaloInvalidoException(String message){
		super(message);
	}
}
