package repositorios;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Repositorio<T> {

	private ArrayList<T> lista;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public Repositorio(){
		lista = new ArrayList<T>();
	}
	public Repositorio(ArrayList<T> objetosTipoT){
		setLista(objetosTipoT);
	}
	
	public void agregar(T objetoTipoT){
		lista.add(objetoTipoT);
	}

	public ArrayList<T> getLista() {
		return lista;
	}

	private void setLista(ArrayList<T> indicadores) {
		this.lista = indicadores;
	}
		
}
