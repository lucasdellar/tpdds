package ui.ViewModels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import comparadores.Comparador;
import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import criterios.Crecimiento;
import domain.Indicador;
import domain.ValorIndicador;
import repositorios.RepositorioIndicadores;
import scala.collection.generic.BitOperations.Int;
import validadores.ValidadorComparador;
import validadores.ValidadorCrecimiento;

@Observable
public class AgregarCondicionCrecimientoViewModel {

	RepositorioIndicadores repositorioIndicadores;
	
	private String mayorMenor;
	private String inicioPeriodo;
	private String finPeriodo;
	private String nombreIndicador;
	private String maxIncumplimientos;
	
	ValidadorComparador validadorComparador;
	ValidadorCrecimiento validadorCrecimiento;
	
	public AgregarCondicionCrecimientoViewModel(RepositorioIndicadores repositorioIndicadores) {
		validadorComparador = new ValidadorComparador();
		validadorCrecimiento = new ValidadorCrecimiento();
		this.repositorioIndicadores = repositorioIndicadores;
	}

	public String getMayorMenor() {
		return mayorMenor;
	}

	public void setMayorMenor(String mayorMenor) {
		this.mayorMenor = mayorMenor;
	}

	public String getInicioPeriodo() {
		return inicioPeriodo;
	}

	public void setInicioPeriodo(String inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	public String getFinPeriodo() {
		return finPeriodo;
	}

	public void setFinPeriodo(String finPeriodo) {
		this.finPeriodo = finPeriodo;
	}

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public void agregarCondicion(List<CondicionTaxativa> condicionesYaAgregadas) {
		validadorComparador.validarString(mayorMenor);
		validadorCrecimiento.validarIntervalo(inicioPeriodo, finPeriodo);
		CondicionTaxativa condicionAAgregar =
				new CondicionTaxativa(repositorioIndicadores, mayorMenor.equals("MAYOR") ? Comparador.MAYOR : Comparador.MENOR);
		condicionAAgregar.setCriterio(new Crecimiento(new ValorIndicador(nombreIndicador, repositorioIndicadores), 
				Integer.parseInt(inicioPeriodo), Integer.parseInt(finPeriodo), Integer.parseInt(getMaxIncumplimientos())));
		condicionesYaAgregadas.add(condicionAAgregar);
	}

	public String getMaxIncumplimientos() {
		return maxIncumplimientos;
	}

	public void setMaxIncumplimientos(String maxIncumplimientos) {
		this.maxIncumplimientos = maxIncumplimientos;
	}

	
}
