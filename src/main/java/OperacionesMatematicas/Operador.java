package OperacionesMatematicas;

public class Operador {

	public static String doTheMath(String numberOne, String numberTwo, char operation) {
		switch (operation) {
		case '/': return (Double.parseDouble(numberOne) / Double.parseDouble(numberTwo)) + ""; 
		case '*': return (Double.parseDouble(numberOne) * Double.parseDouble(numberTwo)) + "";
		case '-': return (Double.parseDouble(numberOne) - Double.parseDouble(numberTwo)) + "";
		default:  return (Double.parseDouble(numberOne) + Double.parseDouble(numberTwo)) + "";
		}
	}
	
}
