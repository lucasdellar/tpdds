package domain;


import org.uqbar.commons.utils.Observable;

import ui.ViewModels.CuentaViewModel;

@Observable
public class Cuenta {
	
	private String empresa;
	private String nombre;
	private String anio;
	private String valor;
	

	public Cuenta() {
	}

	public Cuenta(String empresa, String nombre, String anio, String valor) {
		this.empresa = empresa;
		this.nombre = nombre;
		this.anio = anio;
		this.valor = valor;
	}

	public Cuenta(CuentaViewModel viewModel){
		this.empresa = viewModel.getEmpresa();
		this.nombre = viewModel.getNombre();
		this.anio 	= viewModel.getAnio();
		this.valor = viewModel.getValor();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getAnio() {
		return anio;
	}
	
	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}



}
