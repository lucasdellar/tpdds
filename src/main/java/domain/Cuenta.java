package domain;


import org.uqbar.commons.utils.Observable;

import com.google.gson.Gson;

@Observable
public class Cuenta {
	
	private String nombre;
	private String anio;
	private String patrimonio_neto;
	
	/*public Cuenta(float patrimonio_neto, int anio, String nombre){
		this.patrimonio_neto = patrimonio_neto;
		this.anio = anio;
		this.nombre = nombre;
	}*/
	
	public static Cuenta fromJson(String json){
	    return new Gson().fromJson(json, Cuenta.class);
	}
	
	public String toJson(){
         return new Gson().toJson(this);
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
