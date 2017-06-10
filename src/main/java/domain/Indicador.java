package domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.uqbar.commons.utils.Observable;

import OperacionesMatematicas.ResolutorDeCuentas;
import domain.DomainExceptions.IndicadorInvalidoException;
import repositorios.RepositorioIndicadores;

@Observable
public class Indicador {
	
	private String nombre;
	private String formula;
	RepositorioIndicadores repositorioIndicadores;

	public Indicador(String _nombre, String _formula){
		setNombre(_nombre);
		setFormula(_formula);
		//repositorioIndicadores = new RepositorioIndicadores(); esto rompia por arena, dejarlo para cuando migremos.
	}
	
	
	
	public Double aplicarIndicador(String periodo, Empresa unaEmpresa, RepositorioIndicadores repo){
		repositorioIndicadores = repo;
		formula = formula.replaceAll("\\s+","");
		String cuentaLiteralMatematica = reemplazarPorLasCuentas( filtrarCuentasPorAnio(unaEmpresa.getCuentas(), periodo));
		cuentaLiteralMatematica = reemplazarPorLosIndicadores(cuentaLiteralMatematica, periodo, unaEmpresa);
		if(!parser.Parser.chequearQueQuedenSoloNumeros(cuentaLiteralMatematica))
			throw new IndicadorInvalidoException("Se esta utilizando un indicador sobre una empresa con datos insuficientes.");
		return ResolutorDeCuentas.resolver(cuentaLiteralMatematica);
	}


	private ArrayList<Cuenta> filtrarCuentasPorAnio(ArrayList<Cuenta> cuentas, String periodo) {
		ArrayList<Cuenta> cuentasFiltradas = new ArrayList<Cuenta>();
		for (Cuenta cuenta : cuentas) {
			if(cuenta.getPeriodo().equals(periodo)) 
				cuentasFiltradas.add(cuenta);
		}
		return cuentasFiltradas;
	}



	private String reemplazarPorLosIndicadores(String cuentaMatematica, String periodo, Empresa unaEmpresa){
		String aux = cuentaMatematica;
		for (Indicador indicador : repositorioIndicadores.getIndicadores()) {
			if(cuentaMatematica.contains(indicador.getNombre())) 
				aux = aux.replace(indicador.getNombre(), String.valueOf(indicador.aplicarIndicador(periodo, unaEmpresa, repositorioIndicadores))); 
		}
		return aux;
	}


	private String reemplazarPorLasCuentas(ArrayList<Cuenta> cuentas){
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

