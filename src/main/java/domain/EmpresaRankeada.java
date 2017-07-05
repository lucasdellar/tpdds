package domain;

public class EmpresaRankeada {

	private Empresa empresa;
	private int ranking;
	
	public EmpresaRankeada(Empresa empresa){
		this.setEmpresa(empresa);
		setRanking(0);
	}
	
	public void aumentarRanking(int aumento){
		setRanking(getRanking() + aumento);
	}

	int getRanking() {
		return ranking;
	}

	void setRanking(int ranking) {
		this.ranking = ranking;
	}

	Empresa getEmpresa() {
		return empresa;
	}

	void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
}
