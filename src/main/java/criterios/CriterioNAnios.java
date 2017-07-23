package criterios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import comparadores.IComparador;
import domain.Valor;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;

public class CriterioNAnios extends Criterio{

	int anios;
	
	public CriterioNAnios(Valor valor, int anios) {
		super(valor);
		this.anios = anios;
	}

	public Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador unComparador) {
		List<String> periodos = obtenerPeriodos(unaEmpresa.getCuentas());
		periodos.sort(new Comparator<String>(){
			@Override
			public int compare(String periodo1, String periodo2) {
				return periodo1.compareTo(periodo2);
			}
		});
		List<String> periodosAEvaluar = new ArrayList<String>(periodos.subList(0, anios));
		return periodosAEvaluar.stream().allMatch(unPeriodo -> unComparador.comparar(actualizarPeriodo(unaEmpresa, unPeriodo), unValor));
	}

	@Override
	public double calcular(Empresa unaEmpresa){
		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
	}

//	private void verificarQueNoFaltenCuentas(List<Cuenta> cuentasAEvaluar) {
//		if(Integer.parseInt(cuentasAEvaluar.get(cuentasAEvaluar.size() - 1).getPeriodo()) !=  anios - cuentasAEvaluar.size())
//			throw new CriterioException("Faltan ingresar cuentas para poder evaluar la empresa. Datos insuficientes.");
//	}
//	private Boolean periodoMayor(Cuenta x, Cuenta y) {
//		return Integer.parseInt(x.getPeriodo()) > Integer.parseInt( y .getPeriodo() );
//	}

}
