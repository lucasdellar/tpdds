package ui.ViewModels;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import comparadores.Comparador;
import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.CondicionTaxativa;
import criterios.Mediana;
import criterios.Promedio;
import criterios.Sumatoria;
import domain.ValorNumerico;
import domain.DomainExceptions.OperacionInvalidaException;
import repositorios.RepositorioIndicadores;
import validadores.ValidadorComparador;

@Observable
public class AgregarCondicionMatematicaViewModel {

	RepositorioIndicadores repositorioIndicadores;
	
	private String nombreIndicador;
	private String valor;
	private String operacion;
	private String mayorMenor;
	ValidadorComparador validadorComparador;
	
	public AgregarCondicionMatematicaViewModel(RepositorioIndicadores repositorioIndicadores) {
		this.repositorioIndicadores = repositorioIndicadores;
		validadorComparador = new ValidadorComparador();
	}
	
	public void agregarCondicion(List<CondicionTaxativa> condicionesYaAgregadas) {
		validadorComparador.validarString(getMayorMenor());
		
		CondicionTaxativa condicionAAgregar =
				new CondicionTaxativa(repositorioIndicadores, getMayorMenor().equals("MAYOR") ? Comparador.MAYOR : Comparador.MENOR);
		
		if( getOperacion().equals("sumatoria") )
			condicionAAgregar.setCriterio(new Sumatoria(new ValorNumerico(Double.parseDouble(getValor()))));
		else if( getOperacion().equals("mediana") )
			condicionAAgregar.setCriterio(new Mediana(new ValorNumerico(Double.parseDouble(getValor()))));
		else if( getOperacion().equals("promedio") )
			condicionAAgregar.setCriterio(new Promedio(new ValorNumerico(Double.parseDouble(getValor()))));
		else 
			throw new OperacionInvalidaException("Debe ingresar sumatoria, mediana o promedio");
		
		condicionesYaAgregadas.add(condicionAAgregar);
	}

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getMayorMenor() {
		return mayorMenor;
	}

	public void setMayorMenor(String mayorMenor) {
		this.mayorMenor = mayorMenor;
	}

}
