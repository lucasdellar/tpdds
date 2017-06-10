package repositorios;
/*
 * 
 * 
 * Esta clase no la vamos a usar mas
 * 
 * 
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import domain.Cuenta;


public class RepositorioCuentas {

	private ArrayList<Cuenta> cuentas;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public RepositorioCuentas(){
		cuentas = new ArrayList<Cuenta>();
	}

	public RepositorioCuentas(ArrayList<Cuenta> cuentas){
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

}
