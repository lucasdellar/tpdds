package empresas;

import org.uqbar.commons.utils.Observable;

@Observable
public class EmpresaRankeada{

	Empresa empresa;
	private Integer ranking;
	
	public EmpresaRankeada(Empresa empresa){
		this.empresa = empresa;
		setRanking(0);
	}
	
	public void aumentarRanking(int aumento){
		setRanking(getRanking() + aumento);
	}

	public Integer getRanking() {
		return ranking;
	}

	void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}


}
