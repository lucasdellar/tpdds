package repositorios;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import domain.DomainExceptions.TransactionException;

public class Repositorio<T> implements TransactionalOps {

	private List<T> lista;
	EntityManager manager = PerThreadEntityManagers.getEntityManager();
	
	public Repositorio(){
		lista = new ArrayList<T>();
	}
	public Repositorio(List<T> objetosTipoT){
		setLista(objetosTipoT);
	}
	
	public void persistir(T objetoTipoT){
		withTransaction(() ->manager.persist(objetoTipoT));		
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

	@Override
	public EntityManager entityManager() {
		return manager;
	}
		
}
