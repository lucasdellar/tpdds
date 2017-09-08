package domain;


import javax.persistence.Entity;

import org.uqbar.commons.utils.Observable;

import ui.ViewModels.CuentaViewModel;

@Observable
public class Cuenta implements Comparable<Cuenta>{
	
	private String nombre;
	private String periodo;
	private String valor;
	
	public Cuenta() {
	}

	public Cuenta(String nombre, String periodo, String valor) {
		this.nombre = nombre;
		this.periodo = periodo;
		this.valor = valor;
	}

	public Cuenta(CuentaViewModel viewModel){
		this.nombre = viewModel.getNombre();
		this.periodo 	= viewModel.getPeriodo();
		this.valor = viewModel.getValor();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public int compareTo(Cuenta unaCuenta) {
		return periodo.compareTo(unaCuenta.getPeriodo());
	}

}
