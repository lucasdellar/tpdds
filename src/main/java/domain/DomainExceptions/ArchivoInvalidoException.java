package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class ArchivoInvalidoException extends UserException {
	
	public ArchivoInvalidoException(String message){
		super(message);
	}

}
