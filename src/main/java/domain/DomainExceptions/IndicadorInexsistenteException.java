package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class IndicadorInexsistenteException extends UserException{
	
	public IndicadorInexsistenteException(String message){
		super(message);
	}
}
