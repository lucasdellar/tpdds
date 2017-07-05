package domain;

import java.util.ArrayList;

public class Metodologia {

	ArrayList<EmpresaRankeada> empresasAInvertir; // cumple taxativa y tiene peso
	ArrayList<Empresa> empresasNoInvertir; // no cumple taxativa
	
	ArrayList<CondicionTaxativa> condicionesTaxativas;
	ArrayList<CondicionConPeso> condicionesConPeso;
	Condicion a;

	public Metodologia(ArrayList<CondicionTaxativa> taxativas, ArrayList<CondicionConPeso> conPeso){
		condicionesTaxativas = taxativas;
		condicionesConPeso = conPeso;
		
		empresasAInvertir = new ArrayList<>();
		empresasNoInvertir = new ArrayList<>();
	}
	
	public ArrayList<EmpresaRankeada> aplicarMetodologia(ArrayList<Empresa> empresas){
		
		separarEmpresas(empresas);
		ordenarPorRanking();
		
		return empresasAInvertir;
	}

	private void ordenarPorRanking() {
		for(CondicionConPeso condicion : condicionesConPeso){
			
		}
	}

	private void separarEmpresas(ArrayList<Empresa> empresas) {
		for(Empresa unaEmpresa : empresas){
			if(cumpleTaxativas(unaEmpresa))
				empresasAInvertir.add(new EmpresaRankeada(unaEmpresa));
			else
				empresasNoInvertir.add(unaEmpresa);
		}
	}

	private boolean cumpleTaxativas(Empresa unaEmpresa) {
		return condicionesTaxativas.stream().allMatch(x -> x.cumpleCondicion(unaEmpresa));
	}
	
	
	
}
