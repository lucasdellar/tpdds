package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class ComparadorInvalidoException  extends UserException{
	
	public ComparadorInvalidoException(String message) {
		super(message);
	}
}
