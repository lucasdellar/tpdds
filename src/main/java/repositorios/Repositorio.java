package repositorios;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import domain.DomainExceptions.TransactionException;

public class Repositorio<T> {

	private List<T> lista;
	EntityManager manager = PerThreadEntityManagers.getEntityManager();
	EntityTransaction transaction = manager.getTransaction();

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
	public Repositorio(List<T> objetosTipoT){
		setLista(objetosTipoT);
	}
	
	public void persistir(T objetoTipoT){
		try {
			//if(!transaction.isActive()){
				transaction.begin();
				manager.persist(objetoTipoT);
				transaction.commit();
			//}
		} catch (Exception e) {
			throw new TransactionException("Hay otra transaccion ya activa.");
		}
	}
	
	public void agregar(T objetoTipoT){
		lista.add(objetoTipoT);
		this.persistir(objetoTipoT);
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}
		
}
