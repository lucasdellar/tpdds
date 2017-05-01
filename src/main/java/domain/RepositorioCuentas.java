package domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Cuentas {
	
	private ArrayList<Cuenta> cuentas;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public Cuentas(){
		cuentas = new ArrayList<Cuenta>();
	}

	public Cuentas(ArrayList<Cuenta> cuentas){
		this.cuentas = cuentas;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	} 
	
	public void agregarCuenta(Cuenta unaCuenta){
		cuentas.add(unaCuenta);
	}

	public void ClearCuentas() {
		cuentas.clear();
	}

}
