package criterios;

import java.util.List;
import java.util.stream.Collectors;

import condiciones.Condicion;
import condiciones.DependeDeValor;
import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;

public class CasiSiempre extends CriterioCrecimiento{
	
	private int principio;
	private int fin;
	private int maxIncumplimientos;

	public CasiSiempre(Indicador indicador, int principio, int fin, int maxIncumplimientos) {
		super(indicador);
		this.principio = principio;
		this.fin = fin;
		this.maxIncumplimientos = maxIncumplimientos;
	}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, Condicion unaCondicion) {
		List<Cuenta> cuentasDentroDelIntervalo = unaEmpresa.getCuentas().stream()
				.filter(x -> Integer.parseInt(x.getPeriodo()) > principio 
						||  Integer.parseInt(x.getPeriodo()) > fin).collect(Collectors.toList());
		
		
		return cuentasDentroDelIntervalo.stream()
				.filter( x -> cumple(unaEmpresa, unaCondicion, x, cuentasDentroDelIntervalo))
				.count() >= cuentasDentroDelIntervalo.size() - maxIncumplimientos;
	}

	
}









