package domain;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import OperacionesMatematicas.ResolutorDeCuentas;
import domain.DomainExceptions.IndicadorInvalidoException;
import empresas.Empresa;
import expresiones.Expresion;
import parser.Parser;
import repositorios.RepositorioIndicadores;

// TODO hacer que tambien puede tener indicadores, es decir que no exista expresionCuenta, ni expresionIndicador, sino expresionNoNumerica
// Un test que prueba (y seguramente la funcionalidad) de si la formula esta mal (ver el parser de la branch master)
// Borrar comentarios cuando terminemos, meter mas test y hacer el merge con los otros test que ya teniamos
// Empezar la entrega 4 (?

@Observable
public class Indicador {
	
	private String nombre;
	private String formula;
	private Expresion formula_objetos; // Para testear que funcione y no tocar nada de la UI.
	RepositorioIndicadores repositorioIndicadores;

	public Indicador(String _nombre, String _formula){
		setNombre(_nombre);
		setFormula(_formula);
		// Esto no tiene que ir ac� pero es para ver si funciona.
		Parser parser = new Parser(repositorioIndicadores);
		this.formula_objetos = parser.obtenerExpresion(formula);
		//----------------------------------------------------------
		//repositorioIndicadores = new RepositorioIndicadores(); esto rompia por arena, dejarlo para cuando migremos.
	}
	
	
	
	public Double aplicarIndicador(String periodo, Empresa unaEmpresa, RepositorioIndicadores repo){
//		repositorioIndicadores = repo;
//		formula = formula.replaceAll("\\s+","");
//		String cuentaLiteralMatematica = reemplazarPorLasCuentas( filtrarCuentasPorAnio(unaEmpresa.getCuentas(), periodo));
//		cuentaLiteralMatematica = reemplazarPorLosIndicadores(cuentaLiteralMatematica, periodo, unaEmpresa);
//		if(!parser.Parser.chequearQueQuedenSoloNumeros(cuentaLiteralMatematica))
//			throw new IndicadorInvalidoException("Se esta utilizando un indicador sobre una empresa con datos insuficientes.");
		return formula_objetos.calcular(unaEmpresa, periodo);
	}


	private List<Cuenta> filtrarCuentasPorAnio(List<Cuenta> cuentas, String periodo) {
		ArrayList<Cuenta> cuentasFiltradas = new ArrayList<Cuenta>();
		for (Cuenta cuenta : cuentas) {
			if(cuenta.getPeriodo().equals(periodo)) 
				cuentasFiltradas.add(cuenta);
		}
		return cuentasFiltradas;
	}



	private String reemplazarPorLosIndicadores(String cuentaMatematica, String periodo, Empresa unaEmpresa){
		String aux = cuentaMatematica;
		for (Indicador indicador : repositorioIndicadores.getLista()) {
			if(cuentaMatematica.contains(indicador.getNombre())) 
				aux = aux.replace(indicador.getNombre(), String.valueOf(indicador.aplicarIndicador(periodo, unaEmpresa, repositorioIndicadores))); 
		}
		return aux;
	}

	private String reemplazarPorLasCuentas(List<Cuenta> cuentas){
		String aux = getFormula();
		for (Cuenta cuenta : cuentas) {
			if(getFormula().contains(cuenta.getNombre()))
				aux = aux.replace(cuenta.getNombre(), cuenta.getValor().toString()); 
		}
		return aux;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	
}

