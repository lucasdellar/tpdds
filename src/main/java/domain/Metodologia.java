package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Metodologia {

	String nombre;
	private List<Condicion> condiciones_taxativas;
	private List<Condicion> condiciones_prioritarias;
	
	public Metodologia(String nombre, List<Condicion> condiciones_taxativas, List<Condicion> condiciones_prioritarias) {
		this.nombre = nombre;
		this.condiciones_taxativas = condiciones_taxativas;
		this.condiciones_prioritarias = condiciones_prioritarias;
	}

	public List<EmpresaRankeada> aplicarMetodologia(List<Empresa> empresas){
		List<EmpresaRankeada> misEmpresas = new ArrayList<EmpresaRankeada>();
		inicializarEmpresasRankeadas(misEmpresas, empresas); 
		filtrar_empresas(misEmpresas);
		ordenarPorRanking(misEmpresas);
		
		return misEmpresas;
	}

	private void inicializarEmpresasRankeadas(List<EmpresaRankeada> misEmpresas, List<Empresa> empresas) {
		/* Se crean objetos adicionales para relacionar puntos con cada empresa en particular*/
		for(Empresa empresa : empresas){
			misEmpresas.add(new EmpresaRankeada(empresa.getNombre()));
		}
	}
	
	private void filtrar_empresas(List<EmpresaRankeada> misEmpresas){
		for(Condicion condicion : condiciones_taxativas){
			misEmpresas = condicion.aplicar(misEmpresas);
		}

	}

	public void ordenarPorRanking(List<EmpresaRankeada> misEmpresas) {
		/*Se aplican todas las condiciones, tanto taxativas como prioritarias, 
		 * y luego se ordena la lista por ranking.*/
		for(Condicion condicion : condiciones_prioritarias){
			misEmpresas = condicion.aplicar(misEmpresas);
		}
		
		misEmpresas.sort(new Comparator<EmpresaRankeada>(){
			@Override
			public int compare(EmpresaRankeada emp1, EmpresaRankeada emp2) {
				return emp1.getRanking().compareTo(emp2.getRanking());
			}
		});
	}
	
	// Getters and Setters;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Condicion> getCondiciones_taxativas() {
		return condiciones_taxativas;
	}

	public void setCondiciones_taxativas(List<Condicion> condiciones_taxativas) {
		this.condiciones_taxativas = condiciones_taxativas;
	}

	public List<Condicion> getCondiciones_prioritarias() {
		return condiciones_prioritarias;
	}

	public void setCondiciones_prioritarias(List<Condicion> condiciones_prioritarias) {
		this.condiciones_prioritarias = condiciones_prioritarias;
	}

}