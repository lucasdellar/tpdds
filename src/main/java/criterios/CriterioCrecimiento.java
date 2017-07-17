package criterios;

import java.util.List;
import java.util.stream.Collectors;

import comparadores.IComparador;
import domain.Cuenta;
import domain.Valor;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;

public class CriterioCrecimiento extends Criterio {
	
	protected int principio;
	protected int fin;
	private int maxIncumplimientos;
	
	public CriterioCrecimiento(Valor valor, int principio, int fin, int maxIncumplimientos) {
		super(valor);
		this.principio = principio;
		this.fin = fin;
		this.maxIncumplimientos = maxIncumplimientos;
	}

	public List<Cuenta> obtener_cuentasDentroDelIntervalo(Empresa unaEmpresa){
		return unaEmpresa.getCuentas()
				.stream()
				.filter(unaCuenta -> Integer.parseInt(unaCuenta.getPeriodo()) > principio 
						||  Integer.parseInt(unaCuenta.getPeriodo()) > fin).collect(Collectors.toList());
	}
	
	public Boolean cumple(Empresa unaEmpresa, Cuenta unaCuenta, IComparador unComparador, List<Cuenta> cuentasDentroDelIntervalo) {
			int posicion = cuentasDentroDelIntervalo.indexOf(unaCuenta);
			
			double unValor = valor.calcular(unaEmpresa);
			
			valor.setPeriodo(cuentasDentroDelIntervalo.get(posicion + 1).getPeriodo());
			double otroValor = valor.calcular(unaEmpresa);
			
			return posicion == cuentasDentroDelIntervalo.size() - 1 || unComparador.comparar(unValor, otroValor);	
		}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, IComparador unComparador) {
		List<Cuenta> cuentasDentroDelIntervalo = obtener_cuentasDentroDelIntervalo(unaEmpresa);

		return cuentasDentroDelIntervalo.stream()
				.filter( unaCuenta -> cumple(unaEmpresa, unaCuenta, unComparador, cuentasDentroDelIntervalo))
				.count() >= cuentasDentroDelIntervalo.size() - maxIncumplimientos;
	}
	
	@Override
	public double calcular(Empresa unaEmpresa){
		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
	}
}
