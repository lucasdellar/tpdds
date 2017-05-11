package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.Archivo;

@Observable
public class ArchivoViewModel {

	String ruta;
	
	
	public String getRuta(){
		return this.ruta;
	}
	
	public void setRuta(String ruta){
		this.ruta = ruta;
	}
	
}
