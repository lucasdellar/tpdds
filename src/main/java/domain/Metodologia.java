package domain;

import java.util.ArrayList;
import java.util.Comparator;

import Condiciones.Condicion;
import empresas.Empresa;
import empresas.EmpresaRankeada;

public class Metodologia {

	String nombre;
	ArrayList<Condicion> condiciones;

	public Metodologia(String nombre, ArrayList<Condicion> condiciones){
		this.nombre = nombre;
		this.condiciones = condiciones;
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

	private void ordenarPorRanking(ArrayList<EmpresaRankeada> misEmpresas) {
		
		for(Condicion condicion : condiciones){
			condicion.aplicarCondicion(misEmpresas);
		}
		//misEmpresas.sort( (x, y) -> x.getRanking() > y.getRanking()); PREGUNTAR
	}
	
}