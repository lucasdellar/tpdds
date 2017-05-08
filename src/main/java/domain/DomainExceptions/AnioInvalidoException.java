package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class AnioInvalidoException extends UserException{
	
	public AnioInvalidoException(String message) {
		super(message);
	}
}
