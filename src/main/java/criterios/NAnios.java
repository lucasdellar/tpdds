package criterios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;

import comparadores.Comparador;
import domain.Valor;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;

@Entity
public class NAnios extends Criterio{

	int anios;
	
	private NAnios(){}
	
	public NAnios(Valor valor, int anios) {
		super(valor);
		this.anios = anios;
	}

	public Boolean aplicar(Empresa unaEmpresa, double unValor, Comparador unComparador) {
		List<String> periodos = obtenerPeriodos(unaEmpresa.getCuentas());
		periodos.sort(Comparator.comparing(unPeriodo -> Integer.parseInt(unPeriodo) ));
		
		List<String> periodosAEvaluar = new ArrayList<String>(periodos.subList(0, anios));
		return periodosAEvaluar.stream().allMatch(unPeriodo -> unComparador.comparar(actualizarPeriodo(unaEmpresa, unPeriodo), unValor));
	}

	@Override
	public double calcular(Empresa unaEmpresa){
		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
	}

}
