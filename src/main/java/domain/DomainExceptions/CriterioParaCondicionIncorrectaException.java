package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class CriterioParaCondicionIncorrectaException extends UserException {
	
	public CriterioParaCondicionIncorrectaException(String message){
		super(message);
	}
}
