package empresas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;

@Observable
@Table(name = "Empresas")
@Entity
public class Empresa {

	@Id
	@GeneratedValue	
	long id;
	@Column(name = "nombre")
	public String nombre;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "Empresa_id")
	public List<Cuenta> cuentas;
	
	private Empresa(){}

	public Empresa(String aName){
		this.nombre = aName;
		this.cuentas = new ArrayList<Cuenta>();
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public String getNombre() {
		return nombre;
	}
	
	public List<String> getPosibilesPeriodosOrdenados(){
		HashSet<String> hash = new HashSet( this.getCuentas().stream().map( x -> x.getPeriodo()).collect(Collectors.toList()));
		List<String> posiblesPeriodos = new ArrayList<String>(hash);
		posiblesPeriodos.sort(Comparator.comparing(periodo -> Integer.valueOf(periodo)));
		return posiblesPeriodos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarCuenta(Cuenta unaCuenta){
		cuentas.add(unaCuenta); 
	}
	
	public long getId() {
		return id;
	 }
}