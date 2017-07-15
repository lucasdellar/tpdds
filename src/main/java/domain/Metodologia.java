package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import condiciones.Condicion;
import condiciones.CondicionPrioridad;
import condiciones.CondicionTaxativa;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Metodologia {

	String nombre;
	private List<Condicion> condiciones;
	
	public Metodologia(String nombre, List<Condicion> condiciones) {
		this.nombre = nombre;
		this.condiciones = condiciones;
	}

	public List<EmpresaRankeada> aplicarMetodologia(List<Empresa> empresas){
		List<EmpresaRankeada> misEmpresas = new ArrayList<EmpresaRankeada>();
		inicializarEmpresasRankeadas(misEmpresas, empresas); 
		ordenarPorRanking(misEmpresas);
		
		return misEmpresas;
	}

	private void inicializarEmpresasRankeadas(List<EmpresaRankeada> misEmpresas, List<Empresa> empresas) {
		/* Se crean objetos adicionales para relacionar puntos con cada empresa en particular*/
		for(Empresa empresa : empresas){
			misEmpresas.add(new EmpresaRankeada(empresa.getNombre()));
		}
	}

	public void ordenarPorRanking(List<EmpresaRankeada> misEmpresas) {
		/*Se aplican todas las condiciones, tanto taxativas como prioritarias, 
		 * y luego se ordena la lista por peso.*/
		for(Condicion condicion : condiciones){
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

	public List<Condicion> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(List<Condicion> condiciones) {
		this.condiciones = condiciones;
	}
	
}