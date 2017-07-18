package ui.ViewModels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import condiciones.Condicion;
import domain.Metodologia;
import manejadoresArchivo.ManejadorDeArchivoMetodologias;

@Observable
public class AgregarMetodologiaViewModel {
	
	String nombre; 
	private ArrayList<Condicion> condiciones;
	private ManejadorDeArchivoMetodologias manejador;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public AgregarMetodologiaViewModel(String rutaArchivo) {
		this.manejador = new ManejadorDeArchivoMetodologias(rutaArchivo);
	//	condiciones = new ArrayList<>();
	}
	
	public ArrayList<Condicion> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(ArrayList<Condicion> condiciones) {
		this.condiciones = condiciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarMetodologia() {
		//manejador.agregarMetodologiaAlArchivo(new Metodologia(nombre, condiciones));
	}

}
