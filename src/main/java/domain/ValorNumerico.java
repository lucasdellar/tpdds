package domain;

import java.util.List;
import java.util.stream.Collectors;

import empresas.Empresa;

public class ValorNumerico implements Valor {
	
	Double numero;
	
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
