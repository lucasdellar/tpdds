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
	private List<CondicionTaxativa> condiciones_taxativas;
	private List<CondicionPrioritaria> condiciones_prioritarias;

	public Metodologia(String nombre, List<CondicionTaxativa> condiciones_taxativas, List<CondicionPrioritaria> condiciones_prioritarias) {
		this.nombre = nombre;
		this.condiciones_taxativas = condiciones_taxativas;
		this.condiciones_prioritarias = condiciones_prioritarias;
	}

	public List<EmpresaRankeada> aplicarMetodologia(List<Empresa> empresas, String periodo){
		List<EmpresaRankeada> misEmpresas = new ArrayList<EmpresaRankeada>();
		List <Empresa> empresas_a_invertir = obtener_empresas_a_invertir(empresas);
		inicializarEmpresasRankeadas(misEmpresas, empresas_a_invertir); // Generamos una abstracción adicional para el puntaje.
		ordenarPorRanking(misEmpresas);
		
		return misEmpresas;
	}

	private void inicializarEmpresasRankeadas(List<EmpresaRankeada> misEmpresas, List<Empresa> empresas) {
		for(Empresa empresa : empresas){
			misEmpresas.add(new EmpresaRankeada(empresa.getNombre()));
		}
	}

	public List<Empresa> obtener_empresas_a_invertir(List<Empresa> empresas){
		List <Empresa> empresas_a_invertir = new ArrayList<>();
		for (Empresa empresa : empresas) {
			if(condiciones_taxativas.stream().allMatch(condicion -> condicion.aplicar(empresa))){
				empresas_a_invertir.add(empresa);
			}
		}
		
		return empresas_a_invertir;
	}
	
	public void ordenarPorRanking(List<EmpresaRankeada> misEmpresas) {
		
		for(CondicionPrioritaria condicion_prioritaria : getCondicionesPrioritarias()){
			condicion_prioritaria.aplicar(misEmpresas);
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

	public List<CondicionTaxativa> getCondiciones_taxativas() {
		return condiciones_taxativas;
	}

	public void setCondiciones_taxativas(List<CondicionTaxativa> condiciones_taxativas) {
		this.condiciones_taxativas = condiciones_taxativas;
	}

	public List<CondicionPrioritaria> getCondicionesPrioritarias() {
		return condiciones_prioritarias;
	}

	public void setCondicionesPrioritarias(List<CondicionPrioritaria> condiciones_prioritarias) {
		this.condiciones_prioritarias = condiciones_prioritarias;
	}

}