package ui;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import domain.RepositorioCuentas;
import domain.Validador;
import org.uqbar.commons.utils.Observable;

import domain.Cuenta;


@Observable
public class InviertiendoViewModel {
	private RepositorioCuentas repositorioCuentas;
	private Cuenta nuevaCuenta = new Cuenta();


	
	public void mostrarCuentas() {
		throw new org.uqbar.commons.model.UserException("TIRALAAAAAAA");
	}

	public void agregarCuenta() throws FileNotFoundException, UnsupportedEncodingException, Exception {
		Validador.validarCuenta(nuevaCuenta);
	//	new ManejadorDeArchivoCuentas("repositorioCuentas.txt").agregarCuentaAlArchivo(getNuevaCuenta());
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

}
