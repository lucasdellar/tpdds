package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class NombreInvalidoException extends UserException {

	public NombreInvalidoException(String message){
		super(message);
	}
}
