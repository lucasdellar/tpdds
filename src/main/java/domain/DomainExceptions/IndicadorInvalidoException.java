package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class IndicadorInvalidoException extends RuntimeException {
	
	public IndicadorInvalidoException(String message){
		super(message);
	}

}
