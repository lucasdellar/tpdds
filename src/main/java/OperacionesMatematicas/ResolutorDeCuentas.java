package OperacionesMatematicas;

import java.util.ArrayList;

public class ResolutorDeCuentas {

	public static double resolver(String cuenta){
		return resolverOperaciones(cuentaEnNumerosYOperaciones(cuenta.trim()));
	}

	private static double resolverOperaciones(ArrayList<String> listaDeNumerosYOperaciones) {

		ArrayList<Character> operaciones = listaDeOperaciones();
		for(int i = 0; i < listaDeNumerosYOperaciones.size(); i++) {
			resolverOperacion(operaciones.get(i), listaDeNumerosYOperaciones);
		}
		return Double.parseDouble(listaDeNumerosYOperaciones.get(0));
	}

	private static void resolverOperacion(char c, ArrayList<String> listaDeNumerosYOperaciones) {
		
//		while(listaDeNumerosYOperaciones.contains("" + c)){
//			for(int i = 0; i < listaDeNumerosYOperaciones.size(); i++){
//				if(listaDeNumerosYOperaciones.get(i).equals(""+c)){
//					listaDeNumerosYOperaciones.set( i - 1, Operador.doTheMath(listaDeNumerosYOperaciones.get(i-1), listaDeNumerosYOperaciones.get(i+1), c));
//					listaDeNumerosYOperaciones.remove(i+1);
//					listaDeNumerosYOperaciones.remove(i);
//				}
//			}
//		}
	}

	private static ArrayList<String> cuentaEnNumerosYOperaciones(String cuenta) {
		
		int counter = 0;
		String aux = "";
		ArrayList<String> listaDeNumerosYOperaciones = new ArrayList<>();
		ArrayList<Character> operaciones = listaDeOperaciones();
		
		while(counter < cuenta.length()){
			
			while(counter < cuenta.length() && !operaciones.contains(cuenta.toCharArray()[counter])){
				aux += cuenta.toCharArray()[counter];
				counter++;
			}
			listaDeNumerosYOperaciones.add(aux);
			aux = "";
			if(counter < cuenta.length()) listaDeNumerosYOperaciones.add(cuenta.toCharArray()[counter] + "");
			counter++;
		}
		return listaDeNumerosYOperaciones;
	}

	private static ArrayList<Character> listaDeOperaciones() {
		ArrayList<Character> operaciones = new ArrayList<Character>();
		operaciones.add('/');
		operaciones.add('*');
		operaciones.add('+');
		operaciones.add('-');
		return operaciones;
	}
	
}
