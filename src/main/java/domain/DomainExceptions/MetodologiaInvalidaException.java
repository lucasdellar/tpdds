package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class MetodologiaInvalidaException extends UserException {
	
	public MetodologiaInvalidaException(String message){
		super(message);
	}
}
