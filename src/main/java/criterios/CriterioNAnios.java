package criterios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import condiciones.Condicion;
import domain.Cuenta;
import domain.Indicador;
import domain.DomainExceptions.CriterioException;
import empresas.Empresa;

public class CriterioNAnios extends Criterio{

	int anios;
	
	public CriterioNAnios(Indicador indicador, int anios) {
		super(indicador);
		this.anios = anios;
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, Condicion unaCondicion) {
		unaEmpresa.getCuentas().sort(new Comparator<Cuenta>(){
			@Override
			public int compare(Cuenta cuenta1, Cuenta cuenta2) {
				return cuenta2.getPeriodo().compareTo(cuenta1.getPeriodo());
			}
		});

		List<Cuenta> cuentasAEvaluar = new ArrayList<Cuenta>(unaEmpresa.getCuentas().subList(0, anios));
		verificarQueNoFaltenCuentas(cuentasAEvaluar);
		return cuentasAEvaluar.stream().allMatch( x -> unaCondicion.getComparador().
				comparar(this.getIndicador().aplicarIndicador(x.getPeriodo(), 
						unaEmpresa, unaCondicion.getRepoIndicadores()), unaCondicion.getValue()));
	}

	private void verificarQueNoFaltenCuentas(List<Cuenta> cuentasAEvaluar) {
		if(Integer.parseInt( cuentasAEvaluar.get(cuentasAEvaluar.size() - 1).getPeriodo()) !=  anios - cuentasAEvaluar.size())
			throw new CriterioException("Faltan ingresar cuentas para poder evaluar la empresa. Datos insuficientes.");
		
	}

	private Boolean periodoMayor(Cuenta x, Cuenta y) {
		return Integer.parseInt(x.getPeriodo()) > Integer.parseInt( y .getPeriodo() );
	}

}
