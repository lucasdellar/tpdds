package criterios;

import java.util.List;
import java.util.stream.Collectors;

import condiciones.Condicion;
import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;

public abstract class CriterioCrecimiento extends Criterio {
	
	protected int principio;
	protected int fin;
	
	public CriterioCrecimiento(Indicador indicador, int principio, int fin) {
		super(indicador);
		this.principio = principio;
		this.fin = fin;
	}
	
	 /* Ésta clase sólo se utiliza para generalizar comportamiento entre
	  * CrecimientoCasiSiempre y CrecimientoSiempre. No se utilizará como
	  * criterio en sí mismo.
	  */
	
	public List<Cuenta> obtener_cuentasDentroDelIntervalo(Empresa unaEmpresa){
		return unaEmpresa.getCuentas()
				.stream()
				.filter(unaCuenta -> Integer.parseInt(unaCuenta.getPeriodo()) > principio 
						||  Integer.parseInt(unaCuenta.getPeriodo()) > fin).collect(Collectors.toList());
	}
	
	public Boolean cumple(Empresa unaEmpresa, Condicion unaCondicion, Cuenta unaCuenta, List<Cuenta> cuentasDentroDelIntervalo) {
			int posicion = cuentasDentroDelIntervalo.indexOf(unaCuenta);
			return posicion == cuentasDentroDelIntervalo.size() - 1 || unaCondicion.getComparador()
					.comparar(getIndicador().aplicarIndicador(unaCuenta.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()),
							getIndicador().aplicarIndicador(cuentasDentroDelIntervalo.get(posicion + 1)
									.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()));	
		}

}
