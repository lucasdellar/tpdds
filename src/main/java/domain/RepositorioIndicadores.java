package domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class RepositorioIndicadores {
	
	private ArrayList<Indicador> indicadores;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	   propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public RepositorioIndicadores(){
		indicadores = new ArrayList<Indicador>();
	}
	public RepositorioIndicadores(ArrayList<Indicador> indicadores){
		setIndicadores(indicadores);
	}

	
	public void agregarIndicador(Indicador unIndicador){
		indicadores.add(unIndicador);
	}

	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	private void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
}
