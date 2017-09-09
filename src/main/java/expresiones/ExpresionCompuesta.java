package expresiones;

import OperacionesMatematicas.Operador;
import empresas.Empresa;

public class ExpresionCompuesta extends Expresion {

	Expresion primeraExpresion;
	Expresion segundaExpresion;
	Operador operador;
	
	public ExpresionCompuesta(Expresion unaExpresion, Operador unOperador, Expresion otraExpresion){
		this.primeraExpresion = unaExpresion;
		this.operador = unOperador;
		this.segundaExpresion = otraExpresion;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo){
		return operador.calcular(primeraExpresion.calcular(unaEmpresa, unPeriodo),
								 segundaExpresion.calcular(unaEmpresa, unPeriodo));
	}
}
