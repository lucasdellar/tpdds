package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class ParserException  extends UserException{
	
	public ParserException(String message){
		super(message);
	}
}