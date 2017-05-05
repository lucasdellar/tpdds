package domain;

import org.uqbar.commons.model.UserException;

public class PatrimonioInvalidoException extends UserException {
	
	public PatrimonioInvalidoException(String message){
		super(message);
	}
}
