package condiciones;

import java.util.Comparator;

import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;

public class CriterioNAnios extends Criterio{

	public CriterioNAnios(Indicador indicador) {
		super(indicador);
	}

	@Override
	public Boolean aplicarCriterio(Empresa unaEmpresa, IComparador unComparador, int valor, int anios) {
		unaEmpresa.getCuentas().sort(null);
		// ordenar por las cuentas
		// hacer un take de los anios de la lista guardandolo en una lista de cuentas
		// hacer un foreach de que todas complan el comparador con el valor.
		return ;
	}

	private Boolean periodoMayor(Cuenta x, Cuenta y) {
		return Integer.parseInt(x.getPeriodo()) > Integer.parseInt( y .getPeriodo() );
	}

}
