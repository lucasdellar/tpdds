package domain;

import javax.persistence.Column;

import empresas.Empresa;

public class ValorNumerico extends Valor {
	
	@Column(name = "numero")
	Double numero;
	
	private ValorNumerico(){}
	
	public ValorNumerico(Double numero){
		this.numero = numero;
	}
	
	public Double calcular(Empresa unaEmpresa) {
		return numero; 
	}

	public void setPeriodo(String periodo) {
		
	}

	@Override
	public Indicador getIndicador() {
		return null;
	}

}
