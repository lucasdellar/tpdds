package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.uqbar.commons.utils.Observable;

import domain.Cuenta;
import domain.Cuentas;
import domain.ManejadorDeArchivoCuentas;


@Observable
public class InviertiendoViewModel {

	Cuentas cuentas;
	Cuenta nuevaCuenta = new Cuenta();
	
	public void mostrarCuentas() throws IOException {
		cuentas = new ManejadorDeArchivoCuentas("cuentas.txt").getCuentas(); 
	}

	public void agregarCuenta() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		new ManejadorDeArchivoCuentas("cuentas.txt").agregarCuentaAlArchivo(nuevaCuenta);
	}

	public Cuenta getNuevaCuenta() {
		return nuevaCuenta;
	}

	public void setNuevaCuenta(Cuenta nuevaCuenta) {
		this.nuevaCuenta = nuevaCuenta;
	}

	public Cuentas getCuentas() {
		return cuentas;
	}

	public void setCuentas(Cuentas cuentas) {
		this.cuentas = cuentas;
	}

}
