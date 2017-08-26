package parser;

import OperacionesMatematicas.Division;
import OperacionesMatematicas.Multiplicacion;
import OperacionesMatematicas.Operador;
import OperacionesMatematicas.Resta;
import OperacionesMatematicas.Suma;
import domain.DomainExceptions.OperacionInvalidaException;
import expresiones.Expresion;
import expresiones.ExpresionCompuesta;
import expresiones.ExpresionCuenta;
import expresiones.ExpresionNumero;

public class Parser {

	public Expresion obtenerExpresion(String formula){
		String formula_t = formula.replaceAll("\\s+","");
		String expresion_1;
		Operador unOperador;
		Expresion expresion_2;
		
		/* WARNING: Parsea unicamente de izquierda a derecha, independientemente 
		 * de las precedencias de los operadores. 
		 * Ejemplos: 4 + 4 / 2 = 4. || 4 / 2 * 10 = 20.
		 */
		
		int size = formula_t.length();
		int pos_ultimo_operador = posicion_ultimo_operador(formula_t);
		
		if(esExpresionCompuesta(formula_t)) {
			
			/* Si la expresion es compuesta entonces agarramos todo lo que esté a la izquierda
			 * del último operador hallado en la fórmula y lo referenciamos con expresion_1.*/
			
			expresion_1 = formula_t.substring(0, pos_ultimo_operador-1);
			unOperador = generar_operador(formula_t.charAt(pos_ultimo_operador-1));
			expresion_2 = obtenerExpresionPrimitiva(formula_t.substring(pos_ultimo_operador, formula_t.length()));
			
			/* La expresion compuesta es del tipo: Exp1 Operador Exp2
			 * Como la Exp2 es la última se asume que va a ser o un número o una cuenta. Por eso "primitiva".*/
		} else {
			//En caso de que la expresion no sea compuesta, solo puede ser una cuenta o un número.
			return obtenerExpresionPrimitiva(formula_t);
		}
		return new ExpresionCompuesta(obtenerExpresion(expresion_1), unOperador, expresion_2);
	}
	
	
	private Expresion obtenerExpresionPrimitiva(String exp){
		/* Hipótesis: no van a existir cuentas con números en sus nombres. Se chequea
		 * si el string tiene números. Si tiene alguno lo considera todo como número e intenta
		 * convertirlo a Double. Si no tiene numeros, es una Cuenta.
		 */
		if(exp.matches(".*\\d+.*"))
			return new ExpresionNumero(Double.parseDouble(exp));
		return new ExpresionCuenta(exp);
	}
	
	private int posicion_ultimo_operador(String formula){
		// Algoritmia en su estado más puro (?
		int i = formula.length();
		char op;
		while(i > 1){
			op = formula.charAt(i-1);
			if(esOperador(op))
				return i;
			i--;
		}
		
		return -1;
	}
	
	private Operador generar_operador(char operador){
		
		Operador unOperador = new Suma(); // Eclipse jode con instanciar algo...
		
		switch(operador){
		case '+': 
			unOperador = new Suma();
			break;
		case '-':
			unOperador = new Resta();
			break;
		case '*':
			unOperador = new Multiplicacion();
			break;
		case '/':
			unOperador = new Division();
			break;
		}
		
		return unOperador;
	}
	
	private Boolean esExpresionCompuesta(String formula){
		//Es horrible esto :'C
		return formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/");
	}
	
	private Boolean esOperador(char c){
		return c == '+' || c == '-' || c == '*' || c == '/';
	}
}
