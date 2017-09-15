package ui.ViewModels;
import org.uqbar.commons.utils.Observable;

import domain.Cuenta;
import empresas.Empresa;

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
		this.setRepositorioEmpresas(new RepositorioEmpresas((rutaArchivoEmpresas)));
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
