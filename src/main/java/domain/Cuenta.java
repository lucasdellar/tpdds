package domain;


import org.uqbar.commons.utils.Observable;

import com.google.gson.Gson;

@Observable
public class Cuenta {
	
	private String nombre;
	private String anio;
	private String patrimonio_neto;
	

	public Cuenta() {
	}

	public Cuenta(String nombre, String anio, String patrimonio_neto) {
		this.nombre 	= nombre;
		this.anio 		= anio;
		this.patrimonio_neto = patrimonio_neto;
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
	
	public String getPatrimonio_neto() {
		return patrimonio_neto;
	}
	
	public void setPatrimonio_neto(String patrimonio_neto) {
		this.patrimonio_neto = patrimonio_neto;
	}
	
}
