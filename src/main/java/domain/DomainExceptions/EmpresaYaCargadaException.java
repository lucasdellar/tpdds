package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class EmpresaYaCargadaException extends UserException{
	
	public EmpresaYaCargadaException(String message){
		super(message);
	}
}
