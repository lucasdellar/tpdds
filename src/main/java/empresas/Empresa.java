package empresas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import domain.Cuenta;

@Observable
@Entity
public class Empresa {

	@Id
	@GeneratedValue	
	long id;
	public String nombre;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "Empresa_id")
	public List<Cuenta> cuentas;
	
	private Empresa(){}

	public Empresa(String aName){
		this.nombre = aName;
	}

	public List<Cuenta> getCuentas() {
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
//		EntityManager manager = PerThreadEntityManagers.getEntityManager();
//		EntityTransaction tx = manager.getTransaction();
//		tx.begin();
//		manager.clear();
//		manager.persist(this);
//		tx.commit();
	}

	/* public void addPropertyChangeListener(PropertyChangeListener listener) {
		   propertyChangeSupport.addPropertyChangeListener(listener);
	}*/
	
	
}