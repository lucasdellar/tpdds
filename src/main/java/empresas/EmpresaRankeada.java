package empresas;

public class EmpresaRankeada extends Empresa{

	private int ranking;
	
	public EmpresaRankeada(String nombre){
		super(nombre);
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

	
}
