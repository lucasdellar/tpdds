package expresiones;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;


import OperacionesMatematicas.Operador;
import empresas.Empresa;
import repositorios.RepositorioIndicadores;

@Entity
@Table(name="ExpresionesCompuestas")
public class ExpresionCompuesta extends Expresion {

	@JoinColumn(name = "primeraExpresion")
	@OneToOne(cascade = CascadeType.PERSIST)
	Expresion primeraExpresion;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "segundaExpresion")
	Expresion segundaExpresion;
	@Column(name = "operador")
	@Enumerated(EnumType.STRING)
	Operador operador;
	
	private ExpresionCompuesta(){}
	
	public ExpresionCompuesta(Expresion unaExpresion, Operador unOperador, Expresion otraExpresion){
		this.primeraExpresion = unaExpresion;
		this.operador = unOperador;
		this.segundaExpresion = otraExpresion;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo, RepositorioIndicadores repo){
		return operador.calcular(primeraExpresion.calcular(unaEmpresa, unPeriodo, repo),
								 segundaExpresion.calcular(unaEmpresa, unPeriodo, repo));
	}
}
