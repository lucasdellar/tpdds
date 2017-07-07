package ui.ViewModels;

import java.util.ArrayList;

import condiciones.Condicion;
import domain.Metodologia;
import manejadoresArchivo.ManejadorDeArchivoIndicadores;
import manejadoresArchivo.ManejadorDeArchivoMetodologias;

public class AgregarMetodologiaViewModel {
	
	String nombre; 
	ArrayList<Condicion> condiciones;
	private ManejadorDeArchivoMetodologias manejador;

	public AgregarMetodologiaViewModel(String rutaArchivo) {
		this.manejador = new ManejadorDeArchivoMetodologias(rutaArchivo);
	}
	
	public ArrayList<Condicion> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(ArrayList<Condicion> condiciones) {
		this.condiciones = condiciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarMetodologia() {
	//	manejador.agregarMetodologiaAlArchivo(new Metodologia(nombre, condiciones));
	}

}
