package ui.ViewModels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import domain.Archivo;
import domain.Cuenta;
import domain.Empresa;
import domain.ManejadorDeArchivoEmpresas;
import domain.ValidadorEmpresa;

@Observable
public class AgregarEmpresaViewModel {

	public String empresa;
	private Archivo file;
	private ManejadorDeArchivoEmpresas manejador;
	private ValidadorEmpresa validador;
	
	public AgregarEmpresaViewModel(Archivo aFile){
		file = aFile;
		validador = new ValidadorEmpresa();
	    manejador = new ManejadorDeArchivoEmpresas(file.getRuta());
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		validador.validarNombre(empresa);
		 this.empresa = empresa;
	}
	
	public void agregarEmpresa(){
		Empresa myEmpresa = new Empresa(empresa);
		myEmpresa.setCuentas(new ArrayList<Cuenta>());
		manejador.agregarEmpresaAlArchivo(myEmpresa);
	}
	
}
