package expresiones;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import OperacionesMatematicas.Operador;
import empresas.Empresa;

@Entity
@Table(name="ExpresionesCompuestas")
public class ExpresionCompuesta extends Expresion {

	@OneToOne(cascade = CascadeType.PERSIST)
	Expresion primeraExpresion;
	@OneToOne(cascade = CascadeType.PERSIST)
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
