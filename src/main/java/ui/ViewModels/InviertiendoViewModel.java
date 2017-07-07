package ui.ViewModels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import domain.Cuenta;
import empresas.Empresa;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import repositorios.Repositorio;
import repositorios.RepositorioEmpresas;


@Observable
public class InviertiendoViewModel {
	
	private Cuenta nuevaCuenta = new Cuenta();
	private String rutaArchivoEmpresas;
	private Empresa empresa;
	private RepositorioEmpresas repositorioEmpresas;



	public Cuenta getNuevaCuenta() {
		return nuevaCuenta;
	}

	public void setNuevaCuenta(Cuenta nuevaCuenta) {
		this.nuevaCuenta = nuevaCuenta;
	}

	
	public String getRutaArchivo() {
		return rutaArchivoEmpresas;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivoEmpresas = rutaArchivo;
	}
	
	public void setRepositorioEmpresas(RepositorioEmpresas repoEmpresas)
	{
		this.repositorioEmpresas = repoEmpresas;
	}
	
	public RepositorioEmpresas getRepositorioEmpresas() {
		return repositorioEmpresas;
	}

	public void actualizarEmpresas() {
		this.setRepositorioEmpresas(new ManejadorDeArchivoEmpresas(rutaArchivoEmpresas).getRepositorioEmpresas());
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
