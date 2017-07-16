package empresas;

public class EmpresaRankeada extends Empresa{

	private Integer ranking;
	
	public EmpresaRankeada(String nombre){
		super(nombre);
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

}
