package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uqbar.commons.utils.Observable;

import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import empresas.Empresa;
import empresas.EmpresaRankeada;

@Entity
@Observable
public class Metodologia {
	
	@Id@GeneratedValue
	long id;
	@Column(name = "nombre")
	String nombre;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_condicion")
	private List<CondicionTaxativa> condiciones_taxativas;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_condicion")
	private List<CondicionPrioritaria> condiciones_prioritarias;
	
	private Metodologia(){}
	
	public Metodologia(String nombre, List<CondicionTaxativa> condiciones_taxativas, List<CondicionPrioritaria> condiciones_prioritarias) {
		this.nombre = nombre;
		this.condiciones_taxativas = condiciones_taxativas;
		this.condiciones_prioritarias = condiciones_prioritarias;
	}

	public List<EmpresaRankeada> aplicarMetodologia(List<Empresa> empresas){
		List<EmpresaRankeada> misEmpresas = new ArrayList<EmpresaRankeada>();
		inicializarEmpresasRankeadas(misEmpresas, empresas); 
		misEmpresas = filtrar_empresas(misEmpresas);
		ordenarPorRanking(misEmpresas);
		
		return misEmpresas;
	}

	private void inicializarEmpresasRankeadas(List<EmpresaRankeada> misEmpresas, List<Empresa> empresas) {
		
		/* Se crean objetos adicionales para relacionar puntos con cada empresa en particular*/
		
		for(Empresa empresa : empresas){
			misEmpresas.add(new EmpresaRankeada(empresa));
		}
	}
	
	private List<EmpresaRankeada> filtrar_empresas(List<EmpresaRankeada> misEmpresas){
		for(CondicionTaxativa condicion : condiciones_taxativas){
			misEmpresas = condicion.aplicar(misEmpresas);
		}
		return misEmpresas;
	}

	public void ordenarPorRanking(List<EmpresaRankeada> misEmpresas) {
		
		/*Se aplican todas las condiciones, tanto taxativas como prioritarias, 
		 * y luego se ordena la lista por ranking.*/
		
		for(CondicionPrioritaria condicion : condiciones_prioritarias){
			misEmpresas = condicion.aplicar(misEmpresas);
		}
		
		misEmpresas.sort(Comparator.comparing(EmpresaRankeada::getRanking));
		Collections.reverse(misEmpresas); // La primera EmpresaRankeada es la de mayor peso.
	}
	
	// Getters and Setters;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CondicionTaxativa> getCondiciones_taxativas() {
		return condiciones_taxativas;
	}

	public void setCondiciones_taxativas(List<CondicionTaxativa> condiciones_taxativas) {
		this.condiciones_taxativas = condiciones_taxativas;
	}

	public List<CondicionPrioritaria> getCondiciones_prioritarias() {
		return condiciones_prioritarias;
	}

	public void setCondiciones_prioritarias(List<CondicionPrioritaria> condiciones_prioritarias) {
		this.condiciones_prioritarias = condiciones_prioritarias;
	}

}