package criterios;

import java.util.List;
import java.util.stream.Collectors;

import condiciones.Condicion;
import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;

public class CrecimientoSiempre extends CriterioCrecimiento{

	int principio;
	int fin;
	
	public CrecimientoSiempre(Indicador indicador, int principio, int fin) {
		super(indicador);
		this.principio = principio;
		this.fin = fin;
	}

	@Override
	public Boolean aplicarCriterio(Empresa unaEmpresa, Condicion unaCondicion) {
		
		List<Cuenta> cuentasDentroDelIntervalo = unaEmpresa.getCuentas().stream()
				.filter(x -> Integer.parseInt(x.getPeriodo()) > principio 
						||  Integer.parseInt(x.getPeriodo()) > fin).collect(Collectors.toList());
		
		
		return cuentasDentroDelIntervalo.stream().allMatch( x -> cumple(x, unaCondicion, cuentasDentroDelIntervalo, unaEmpresa));
	}
}
