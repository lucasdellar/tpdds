package expresiones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import empresas.Empresa;
import repositorios.RepositorioIndicadores;

@Entity
@Table(name="ExpresionesNumericas")
public class ExpresionNumero extends Expresion{	

	double num;
	
	private ExpresionNumero(){}
	
	public ExpresionNumero(double numero){
		this.num = numero;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo, RepositorioIndicadores repo){
		return num;
	}

}
