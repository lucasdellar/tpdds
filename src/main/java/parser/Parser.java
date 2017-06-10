package parser;

import java.util.ArrayList;

import domain.DomainExceptions.ParserException;

public class Parser {

	public static void verificarFormato(String posibleCuenta){
		
		String cuentaRegex = "\\s*[\\da-zA-Z]+\\s*([+-/\\*]\\s*[\\da-zA-Z]+\\s*)*$";
		if(!posibleCuenta.matches(cuentaRegex)) 
			throw new ParserException("Formato incorrecto");
	}
	
	
	public static boolean chequearQueQuedenSoloNumeros(String cuentaLiteralMatematica) {
		String cuentaRegex = "\\d+([+-/\\*]\\d+)*$";
		return cuentaLiteralMatematica.replaceAll("\\s+","").matches(cuentaRegex); 
	}
	
	
}
