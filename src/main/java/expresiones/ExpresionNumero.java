package expresiones;

import empresas.Empresa;

public class ExpresionNumero extends Expresion{
	
	double num;
	
	public ExpresionNumero(double numero){
		this.num = numero;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo){
		return num;
	}

}
