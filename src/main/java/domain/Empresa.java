package domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

@Observable
public class Empresa {

	public String nombre;
	public ArrayList<Cuenta> cuentas;
	//private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this); 

	public Empresa(String aName){
		
		this.nombre = aName;
		
		//this.cuentas = new ArrayList<Cuenta>();
		
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarCuenta(Cuenta unaCuenta){
		cuentas.add(unaCuenta);
	}

	/* public void addPropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.addPropertyChangeListener(listener);
	}*/
	
	
}