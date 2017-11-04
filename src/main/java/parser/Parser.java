package parser;

import OperacionesMatematicas.Operador;
import domain.DomainExceptions.OperadorInvalidoException;
import domain.DomainExceptions.ParserException;
import expresiones.Expresion;
import expresiones.ExpresionCompuesta;
import expresiones.ExpresionNoNumerica;
import expresiones.ExpresionNumero;
import repositorios.RepositorioIndicadores;
import scala.Char;

public class Parser {

	RepositorioIndicadores repo;
	
	public Parser(RepositorioIndicadores repo) {
		this.repo = repo;
	}


	public Expresion obtenerExpresion(String formula){
		String formula_t = formula.replaceAll("\\s+","");
		String expresion_1;
		Operador unOperador;
		Expresion expresion_2;
		
		/* WARNING: Parsea unicamente de izquierda a derecha, independientemente 
		 * de las precedencias de los operadores. 
		 * Ejemplos: 4 + 4 / 2 = 4. || 4 / 2 * 10 = 20.
		 */
		
		verificarFormato(formula_t);
		
		int size = formula_t.length();
		int pos_ultimo_operador = posicion_ultimo_operador(formula_t);
		
		if(esExpresionCompuesta(formula_t)) {
			
			/* Si la expresion es compuesta entonces agarramos todo lo que est� a la izquierda
			 * del �ltimo operador hallado en la f�rmula y lo referenciamos con expresion_1.*/
			
			expresion_1 = formula_t.substring(0, pos_ultimo_operador-1);
			unOperador = generar_operador(formula_t.charAt(pos_ultimo_operador-1));
			expresion_2 = obtenerExpresionPrimitiva(formula_t.substring(pos_ultimo_operador, formula_t.length()));
			
			/* La expresion compuesta es del tipo: Exp1 Operador Exp2
			 * Como la Exp2 es la �ltima se asume que va a ser o un n�mero o una cuenta. Por eso "primitiva".*/
		} else {
			//En caso de que la expresion no sea compuesta, solo puede ser una cuenta o un n�mero.
			return obtenerExpresionPrimitiva(formula_t);
		}
		return new ExpresionCompuesta(obtenerExpresion(expresion_1), unOperador, expresion_2);
	}
	
	public static boolean verificarFormato(String posibleCuenta){

		String cuentaRegex = "\\s*[\\da-zA-Z]+\\s*([+-/\\*]\\s*[\\da-zA-Z]+\\s*)*$";
		if(!posibleCuenta.matches(cuentaRegex)) 
			return false;
		return true;
	}
	
	
	private Expresion obtenerExpresionPrimitiva(String exp){
		/* Hip�tesis: no van a existir cuentas con n�meros en sus nombres. Se chequea
		 * si el string tiene n�meros. Si tiene alguno lo considera todo como n�mero e intenta
		 * convertirlo a Double. Si no tiene numeros, es una Cuenta.
		 */
		
		return exp.matches(".*\\d+.*") ?
			 new ExpresionNumero(Double.parseDouble(exp)) 
			: new ExpresionNoNumerica(exp); 
	}
	
	private int posicion_ultimo_operador(String formula){
		// Algoritmia en su estado m�s puro (?
		int i = formula.length();
		char op;
		while(i > 1){
			op = formula.charAt(i-1);
			if(esOperador(Char.box(op)))
				return i;
			i--;
		}
		
		return -1;
	}
	
	private Operador generar_operador(char operador){
		
		switch(operador){
		case '+': 
			return Operador.SUMA;
		case '-':
			return Operador.RESTA;
		case '*':
			return Operador.MULTIPLICACION;
		case '/':
			return Operador.DIVISION;
		}
		
		throw new OperadorInvalidoException("La formula del indicador tiene un operador invalido.");

	}
	
	private Boolean esExpresionCompuesta(String formula){
		//Es horrible esto :'C
		return formula.contains("+") || formula.contains("-") || formula.contains("*") || formula.contains("/");
	}
	
	private Boolean esOperador(Character character){
		return character.equals('+') || character.equals('-')|| character.equals('*') || character.equals('/');
	}
}
