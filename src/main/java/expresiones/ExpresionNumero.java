package expresiones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import empresas.Empresa;

@Entity
@DiscriminatorValue("ExpNum")
public class ExpresionNumero extends Expresion{
	
	@Id@GeneratedValue
	long id;
	double num;
	
	private ExpresionNumero(){}
	
	public ExpresionNumero(double numero){
		this.num = numero;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo){
		return num;
	}

}
