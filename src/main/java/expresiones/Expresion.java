package expresiones;

import domain.DomainExceptions.UnimplementedMethod;
import empresas.Empresa;

public abstract class Expresion{

	public abstract double calcular(Empresa empresa, String periodo);
	
}
