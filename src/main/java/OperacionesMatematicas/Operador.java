package OperacionesMatematicas;

public class Operador {

	public static String doTheMath(String numberOne, String numberTwo, char operation) {
		
		double operador1 = Double.parseDouble(numberOne);
		double operador2 = Double.parseDouble(numberTwo);
		switch (operation) {
		case '/': return Division.operar(operador1, operador2) + ""; 
		case '*': return Multiplicacion.operar(operador1, operador2) + "";
		case '-': return Resta.operar(operador1, operador2) + "";
		default:  return Suma.operar(operador1, operador2) + "";
		}
	}
	
}
