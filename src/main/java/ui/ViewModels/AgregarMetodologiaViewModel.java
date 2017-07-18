package ui.ViewModels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import domain.Metodologia;
import manejadoresArchivo.ManejadorDeArchivoMetodologias;

@Observable
public class AgregarMetodologiaViewModel {
	
	String nombre; 
	private List<CondicionTaxativa> condicionesTaxativas;
	private List<CondicionPrioritaria> condicionesPrioritarias;
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
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarMetodologia() {
		manejador.agregarMetodologiaAlArchivo(new Metodologia(nombre, condicionesTaxativas, condicionesPrioritarias));
	}

	public List<CondicionTaxativa> getCondicionesTaxativas() {
		return condicionesTaxativas;
	}

	public void setCondicionesTaxativas(List<CondicionTaxativa> condicionesTaxativas) {
		this.condicionesTaxativas = condicionesTaxativas;
	}

	public List<CondicionPrioritaria> getCondicionesPrioritarias() {
		return condicionesPrioritarias;
	}

	public void setCondicionesPrioritarias(List<CondicionPrioritaria> condicionesPrioritarias) {
		this.condicionesPrioritarias = condicionesPrioritarias;
	}

}
