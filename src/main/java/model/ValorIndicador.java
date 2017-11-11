package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import empresas.Empresa;
import repositorios.RepositorioIndicadores;

@Entity
public class ValorIndicador extends Valor{
	
	@Column(name = "indicador")
	String indicador;
	@Column(name = "periodo")
	String periodo;
	
	@Transient
	RepositorioIndicadores repo;
	
	private ValorIndicador(){}

	public ValorIndicador(String indicador, RepositorioIndicadores repo) {
		this.indicador = indicador;
		this.repo = repo;
	}

	public Double calcular(Empresa unaEmpresa) {
		return repo.indicadorDesdeString(indicador).aplicarIndicador(this.periodo, unaEmpresa, this.repo);
	}
	
	public void setPeriodo(String unPeriodo){
		this.periodo = unPeriodo;
	}
	
	public Indicador getIndicador() {
		return repo.indicadorDesdeString(indicador);
	}

}
