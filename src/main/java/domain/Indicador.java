package domain;
import org.antlr.*;

public class Indicador {
	
	public String nombre;
	public String formula;

	Indicador(String _nombre, String _formula){
		nombre = _nombre;
		formula = _formula;
	}
	// Ingreso Neto = Op continuadas + Op discotinuadas 
}

