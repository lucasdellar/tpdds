package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class CuentaPreexistenteException extends UserException{
	
	public CuentaPreexistenteException(String message){
		super(message);
	}
}
