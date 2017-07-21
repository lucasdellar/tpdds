package ui.ViewModels;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.CondicionTaxativa;
import criterios.CriterioCrecimiento;
import criterios.CriterioNAnios;
import criterios.Mediana;
import criterios.Promedio;
import criterios.Sumatoria;
import domain.ValorIndicador;
import domain.ValorNumerico;
import domain.DomainExceptions.OperacionInvalidaException;
import repositorios.RepositorioIndicadores;
import validadores.ValidadorComparador;
import validadores.ValidadorCrecimiento;

@Observable
public class AgregarCondicionNAniosViewModel {
	
	public AgregarCondicionNAniosViewModel(RepositorioIndicadores repositorioIndicadores) {
		this.repositorioIndicadores = repositorioIndicadores;
		validadorComparador = new ValidadorComparador();
	}
	
	RepositorioIndicadores repositorioIndicadores;
	
	private String mayorMenor;
	private String nombreIndicador;
	private String anios;
	private ValidadorComparador validadorComparador;
	
	
	
	public void agregarCondicion(List<CondicionTaxativa> condicionesYaAgregadas) {
		validadorComparador.validarString(getMayorMenor());
		
		CondicionTaxativa condicionAAgregar =
				new CondicionTaxativa(repositorioIndicadores, getMayorMenor().equals("MAYOR") ? new ComparadorMayor() : new ComparadorMenor());
		
		condicionAAgregar.setCriterio(new CriterioNAnios(new ValorIndicador(nombreIndicador, repositorioIndicadores), Integer.parseInt(anios)));
		
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
	
	public String getAnios() {
		return anios;
	}
	
	public void setAnios(String anios) {
		this.anios = anios;
	}
	
	

}
