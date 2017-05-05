package domain;

public class CuentaPreexistenteException extends RuntimeException{
	
	public CuentaPreexistenteException(String message){
		super(message);
	}
}