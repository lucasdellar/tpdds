package expresiones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import OperacionesMatematicas.Operador;
import empresas.Empresa;

@Entity
@DiscriminatorValue("ExpComp")
public class ExpresionCompuesta extends Expresion {

	@JoinColumn(name = "exp_id")
	Expresion primeraExpresion;
	@JoinColumn(name = "exp_id")
	Expresion segundaExpresion;
	@Enumerated(EnumType.STRING)
	Operador operador;
	
	private ExpresionCompuesta(){}
	
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
