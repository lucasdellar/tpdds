package ui.ViewModels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import domain.RepositorioCuentas;
import org.uqbar.commons.utils.Observable;

import domain.Cuenta;
import domain.ManejadorDeArchivoCuentas;


@Observable
public class InviertiendoViewModel {
	private RepositorioCuentas repositorioCuentas;
	private Cuenta nuevaCuenta = new Cuenta();
	private String rutaArchivo;


	
	public void mostrarCuentas(){
		repositorioCuentas = new ManejadorDeArchivoCuentas(rutaArchivo).getRepositorioCuentas();
	}

	public Cuenta getNuevaCuenta() {
		return nuevaCuenta;
	}

	public void setNuevaCuenta(Cuenta nuevaCuenta) {
		this.nuevaCuenta = nuevaCuenta;
	}

	public RepositorioCuentas getRepositorioCuentas() {
		return repositorioCuentas;
	}

	public void setRepositorioCuentas(RepositorioCuentas repositorioCuentas) {
		this.repositorioCuentas = repositorioCuentas;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

}
