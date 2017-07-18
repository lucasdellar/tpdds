package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class NoSeCargaronEmpresasException extends UserException {
	
	public NoSeCargaronEmpresasException(String message){
		super(message);
	}
}
