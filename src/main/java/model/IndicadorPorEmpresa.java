package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "IndicadoresPorEmpresa")
@IdClass(IndicadorPorEmpresaPK.class)
@Entity
public class IndicadorPorEmpresa {
	
	@Id
	@JoinColumn
	private long empresa_id;
	@Id
	@JoinColumn(name = "indicador_id")
	private long indicador_id;
	@Id
	@Column(name = "periodo")
	private String periodo;
	@Column(name = "resultado")
	private double resultado;
	
	public IndicadorPorEmpresa() {}
	
	public IndicadorPorEmpresa(long empresa_id, long indicador_id, String periodo, double resultado) {
		this.empresa_id = empresa_id;
		this.indicador_id = indicador_id;
		this.periodo = periodo;
		this.setResultado(resultado);
	}

	public double getResultado() {
		return resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}
	
	
}
