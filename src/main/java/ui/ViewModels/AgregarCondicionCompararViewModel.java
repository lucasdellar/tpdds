package ui.ViewModels;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import comparadores.Comparador;
import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import criterios.Crecimiento;
import criterios.PorValor;
import criterios.Mediana;
import criterios.Promedio;
import criterios.Sumatoria;
import domain.ValorIndicador;
import domain.ValorNumerico;
import domain.DomainExceptions.OperacionInvalidaException;
import repositorios.RepositorioIndicadores;
import validadores.ValidadorComparador;

@Observable
public class AgregarCondicionCompararViewModel {
	
	RepositorioIndicadores repositorioIndicadores;
	private String nombreIndicador;
	private String periodo;
	private String mayorMenor;
	private String peso;
	ValidadorComparador validadorComparador;
	
	

	public AgregarCondicionCompararViewModel(RepositorioIndicadores repositorioIndicadores) {
		
		this.repositorioIndicadores = repositorioIndicadores;
		validadorComparador = new ValidadorComparador();
	}

	public void agregarCondicion(List<CondicionPrioritaria> condicionesYaAgregadas) {
		
		validadorComparador.validarString(getMayorMenor());
		
		CondicionPrioritaria condicionAAgregar =
			new CondicionPrioritaria(repositorioIndicadores, getMayorMenor().equals("MAYOR") ? Comparador.MAYOR : Comparador.MENOR, Integer.parseInt(peso));
		
		ValorIndicador valor = new ValorIndicador(nombreIndicador, repositorioIndicadores);
		valor.setPeriodo(periodo);
		condicionAAgregar.setCriterio(new PorValor(valor));
		condicionesYaAgregadas.add(condicionAAgregar);
	}

	public String getMayorMenor() {
		return mayorMenor;
	}

	public void setMayorMenor(String mayorMenor) {
		this.mayorMenor = mayorMenor;
	}

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

}
