package domain;

import java.util.ArrayList;
import java.util.Comparator;

import condiciones.Condicion;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Metodologia {

	String nombre;
	private ArrayList<Condicion> condiciones;

	public Metodologia(String nombre, ArrayList<Condicion> condiciones){
		this.nombre = nombre;
		this.setCondiciones(condiciones);
	}
	

	public ArrayList<EmpresaRankeada> aplicarMetodologia(ArrayList<Empresa> empresas){
		ArrayList<EmpresaRankeada> misEmpresas = new ArrayList<>();
		inicializarEmpresasRankeadas(misEmpresas, empresas);
		ordenarPorRanking(misEmpresas);
		
		return misEmpresas;
	}

	private void inicializarEmpresasRankeadas(ArrayList<EmpresaRankeada> misEmpresas, ArrayList<Empresa> empresas) {
		for(Empresa empresa : empresas){
			misEmpresas.add(new EmpresaRankeada(empresa.getNombre()));
		}
	}

	public void ordenarPorRanking(ArrayList<EmpresaRankeada> misEmpresas) {
		
		for(Condicion condicion : getCondiciones()){
			condicion.aplicarCondicion(misEmpresas);
		}
		misEmpresas.sort(new Comparator<EmpresaRankeada>(){

			@Override
			public int compare(EmpresaRankeada emp1, EmpresaRankeada emp2) {
				return emp1.getRanking().compareTo(emp2.getRanking());
			}
		});
	}

	public ArrayList<Condicion> getCondiciones() {
		return condiciones;
	}


	private void setCondiciones(ArrayList<Condicion> condiciones) {
		this.condiciones = condiciones;
	}

}