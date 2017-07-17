package criterios;

import java.util.List;
import java.util.stream.Collectors;

import condiciones.Condicion;
import domain.Cuenta;
import domain.Indicador;
import domain.Valor;
import empresas.Empresa;

public abstract class CriterioCrecimiento extends Criterio {
	
	protected int principio;
	protected int fin;
	
	public CriterioCrecimiento(Valor valor, int principio, int fin) {
		super(valor);
		this.principio = principio;
		this.fin = fin;
	}

	public List<Cuenta> obtener_cuentasDentroDelIntervalo(Empresa unaEmpresa){
		return unaEmpresa.getCuentas()
				.stream()
				.filter(unaCuenta -> Integer.parseInt(unaCuenta.getPeriodo()) > principio 
						||  Integer.parseInt(unaCuenta.getPeriodo()) > fin).collect(Collectors.toList());
	}
	
	public Boolean cumple(Empresa unaEmpresa, Condicion unaCondicion, Cuenta unaCuenta, List<Cuenta> cuentasDentroDelIntervalo) {
			int posicion = cuentasDentroDelIntervalo.indexOf(unaCuenta);
			return posicion == cuentasDentroDelIntervalo.size() - 1 || unaCondicion.getComparador()
					.comparar(valor.calcular(unaCuenta.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()),
							valor.calcular(cuentasDentroDelIntervalo.get(posicion + 1)
									.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()));	
		}

}
