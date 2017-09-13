package comparadores;

import domain.DomainExceptions.OperacionInvalidaException;

public enum Comparador {

	  MAYOR{
			public Boolean comparar(double numeroUno, double numeroDos) {
				return numeroUno > numeroDos;
			}
		   },
	  MENOR{
			   public Boolean comparar(double numeroUno, double numeroDos) {
					return numeroUno < numeroDos;
				}
	   };

	
	public abstract Boolean comparar(double numeroUno, double numeroDos);
	
}
