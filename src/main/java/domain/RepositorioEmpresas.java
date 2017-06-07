package domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class RepositorioEmpresas {

	public ArrayList<Empresa> empresas;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.removePropertyChangeListener(listener);
		}
	
	public RepositorioEmpresas() {
		setEmpresas(new ArrayList<Empresa>());
	}

	public RepositorioEmpresas(ArrayList<Empresa> unasEmpresas){
		this.setEmpresas(unasEmpresas);
	}
	
	public void agregarEmpresa(Empresa nombreEmpresa){
		empresas.add(nombreEmpresa);
	}

	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}
}
