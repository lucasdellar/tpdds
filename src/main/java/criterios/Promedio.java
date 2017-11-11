package criterios;

import java.util.List;

import javax.persistence.Entity;

import comparadores.Comparador;
import empresas.Empresa;
import model.Valor;

@Entity
public class Promedio extends Criterio{

	private Promedio(){}
	
	public Promedio(Valor valor) {
		super(valor);
	}
	
	@Override
	public double calcular(Empresa unaEmpresa) {
		double sumatoria = 0;
		List<String> periodos = obtenerPeriodos(unaEmpresa.getCuentas());
		sumatoria = periodos.stream().mapToDouble(unPeriodo -> actualizarPeriodo(unaEmpresa, unPeriodo)).sum();
		
		return sumatoria / periodos.size();
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, Comparador comparador) {
		double promedio = calcular(unaEmpresa);
		return  comparador.comparar(promedio, unValor);
	}
}
