package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.Indicador;
import manejadoresArchivo.ManejadorDeArchivoIndicadores;
import validadores.ValidadorIndicadores;

@Observable
public class AgregarIndicadorViewModel {

	private String nombre;
	private String formula;
	private ManejadorDeArchivoIndicadores manejador;
	private ValidadorIndicadores validador;
	
	
	
	public AgregarIndicadorViewModel(String rutaArchivo){
		manejador = new ManejadorDeArchivoIndicadores(rutaArchivo);
		validador = new ValidadorIndicadores();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public void agregarIndicador() {
		validador.validarIndicador(nombre, formula, manejador);
		//manejador.agregarIndicadorAlArchivo(new Indicador(nombre, formula));
	}
	
}
