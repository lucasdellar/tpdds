package ui.ViewModels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.Condicion;
import condiciones.Crecimiento;
import repositorios.RepositorioIndicadores;
import validadores.ValidadorComparador;
import validadores.ValidadorCrecimiento;

@Observable
public class AgregarCondicionCrecimientoViewModel {

	RepositorioIndicadores repositorioIndicadores;
	
	private String mayorMenor;
	private String inicioPeriodo;
	private String finPeriodo;
	private String nombreIndicador;
	
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

	public void agregarCondicion(ArrayList<Condicion> condicionesYaAgregadas) {
		validadorComparador.validarString(mayorMenor);
		validadorCrecimiento.validarIntervalo(inicioPeriodo, finPeriodo);
		System.out.println(condicionesYaAgregadas.size());
		condicionesYaAgregadas.add(new Crecimiento(repositorioIndicadores, 
				mayorMenor.equals("MAYOR") ? new ComparadorMayor() : new ComparadorMenor() ));
	}

	
	
}
