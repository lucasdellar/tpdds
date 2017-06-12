package ui.ViewModels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import domain.Cuenta;
import domain.Empresa;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import repositorios.Repositorio;


@Observable
public class InviertiendoViewModel {
	
	private Cuenta nuevaCuenta = new Cuenta();
	private String rutaArchivo;
	private Empresa empresa;
	private Repositorio<Empresa> repositorioEmpresas;



	public Cuenta getNuevaCuenta() {
		return nuevaCuenta;
	}

	public void setNuevaCuenta(Cuenta nuevaCuenta) {
		this.nuevaCuenta = nuevaCuenta;
	}

	
	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
	public void setRepositorioEmpresas(Repositorio<Empresa> repoEmpresas)
	{
		this.repositorioEmpresas = repoEmpresas;
	}
	
	public Repositorio<Empresa> getRepositorioEmpresas() {
		return repositorioEmpresas;
	}

	public void actualizarEmpresas() {
		this.setRepositorioEmpresas(new ManejadorDeArchivoEmpresas(rutaArchivo).getRepositorioEmpresas());
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	

}
