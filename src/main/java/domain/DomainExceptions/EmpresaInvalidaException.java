package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class EmpresaInvalidaException extends UserException {
	
	public EmpresaInvalidaException(String message){
		super(message);
	}

}
