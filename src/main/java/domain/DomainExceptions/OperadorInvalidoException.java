package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class OperadorInvalidoException extends UserException {
	
	public OperadorInvalidoException(String message){
		super(message);
	}
}