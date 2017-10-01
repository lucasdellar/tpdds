package domain.DomainExceptions;

import org.uqbar.commons.model.UserException;

public class TransactionException extends UserException {
	
	public TransactionException(String message){
		super(message);
	}

}
