package criterios;

import java.util.List;
import java.util.stream.Collectors;

import comparadores.Comparador;
import domain.Cuenta;
import domain.Valor;
import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
import empresas.Empresa;

public class Crecimiento extends Criterio {
	
	protected int principio;
	protected int fin;
	private int maxIncumplimientos;
	
	public Crecimiento(Valor valor, int principio, int fin, int maxIncumplimientos) {
		super(valor);
		this.principio = principio;
		this.fin = fin;
		this.maxIncumplimientos = maxIncumplimientos;
	}

	public List<Cuenta> obtener_cuentasDentroDelIntervalo(Empresa unaEmpresa){
		return unaEmpresa.getCuentas()
				.stream()
				.filter(unaCuenta ->
				Integer.parseInt(unaCuenta.getPeriodo()) >= principio 
						&&  Integer.parseInt(unaCuenta.getPeriodo()) <= fin).collect(Collectors.toList());
	}
	
	public Boolean cumple(Empresa unaEmpresa, String unPeriodo, Comparador unComparador, List<String> periodosDentroDelIntervalo) {
			double unValor = 0;
			double otroValor = 0;
			int posicion = periodosDentroDelIntervalo.indexOf(unPeriodo);
			if(posicion != periodosDentroDelIntervalo.size() - 1){
				valor.setPeriodo(unPeriodo);
				unValor = valor.calcular(unaEmpresa);
				
				
				valor.setPeriodo(periodosDentroDelIntervalo.get(posicion + 1));
				otroValor = valor.calcular(unaEmpresa);
			}
			return posicion == periodosDentroDelIntervalo.size() - 1 || unComparador.comparar(unValor, otroValor);	
		}

	@Override
	public Boolean aplicar(Empresa unaEmpresa, double unValor, Comparador unComparador) {
		List<Cuenta> cuentasDentroDelIntervalo = obtener_cuentasDentroDelIntervalo(unaEmpresa);
		List<String> periodosDentroDelIntervalo = obtenerPeriodos(cuentasDentroDelIntervalo);
		return periodosDentroDelIntervalo.stream()
				.filter( unPeriodo -> cumple(unaEmpresa, unPeriodo, unComparador, periodosDentroDelIntervalo))
				.count() >= periodosDentroDelIntervalo.size() - maxIncumplimientos;
	}
	
	@Override
	public double calcular(Empresa unaEmpresa){
		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
	}
}
