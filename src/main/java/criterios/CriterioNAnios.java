package criterios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import domain.Cuenta;
import domain.Indicador;
import domain.Valor;
import domain.DomainExceptions.CriterioException;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;

public class CriterioNAnios extends Criterio{

	int anios;
	
	public CriterioNAnios(Valor valor, int anios) {
		super(valor);
		this.anios = anios;
	}

	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
		unaEmpresa.getCuentas().sort(new Comparator<Cuenta>(){
			@Override
			public int compare(Cuenta cuenta1, Cuenta cuenta2) {
				return cuenta2.getPeriodo().compareTo(cuenta1.getPeriodo());
			}
		});

		List<Cuenta> cuentasAEvaluar = new ArrayList<Cuenta>(unaEmpresa.getCuentas().subList(0, anios));
		verificarQueNoFaltenCuentas(cuentasAEvaluar);
		return cuentasAEvaluar.stream().allMatch( x -> unaCondicion.getComparador().
				comparar(this.valor.calcular(x.getPeriodo(), 
						unaEmpresa, unaCondicion.getRepoIndicadores()), unaCondicion.getValor()));
	}

	private void verificarQueNoFaltenCuentas(List<Cuenta> cuentasAEvaluar) {
		if(Integer.parseInt( cuentasAEvaluar.get(cuentasAEvaluar.size() - 1).getPeriodo()) !=  anios - cuentasAEvaluar.size())
			throw new CriterioException("Faltan ingresar cuentas para poder evaluar la empresa. Datos insuficientes.");
		
	}

	private Boolean periodoMayor(Cuenta x, Cuenta y) {
		return Integer.parseInt(x.getPeriodo()) > Integer.parseInt( y .getPeriodo() );
	}

	@Override
	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion) {
		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
	}

}
