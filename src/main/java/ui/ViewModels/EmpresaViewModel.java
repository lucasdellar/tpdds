package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.Archivo;
import domain.Cuenta;
import domain.Empresa;
import domain.ManejadorDeArchivoEmpresas;
import domain.RepositorioEmpresas;
import domain.ValidadorCuenta;
import domain.ValidadorEmpresa;

@Observable

public class EmpresaViewModel {

	    private Empresa empresa;
	    private RepositorioEmpresas empresas;
	    private ValidadorEmpresa validador;

	    private Archivo archivo;


	    public EmpresaViewModel() {
	        validador = new ValidadorEmpresa();
	        
	    }

	    public EmpresaViewModel(Archivo archivo) {
	        this();
	        this.archivo = archivo;
	        empresas = new ManejadorDeArchivoEmpresas(archivo.getRuta()).getRepositorioEmpresas();
	    }

	    public String getNombreEmpresa() {
	        return empresa.getNombre();
	    }

	    public void setNombreEmpresa(String nombre) {
	        validador.validarNombre(nombre);
	        empresa.setNombre(nombre);
	    }

	    public void agregarEmpresa() {
	    	validador.validarQueNoEsteYaCargarda(empresa.getNombre(), new ManejadorDeArchivoEmpresas(archivo.getRuta()).getRepositorioEmpresas());
	        archivo.agregarEmpresa(empresa);
	    }

	    public Archivo getArchivo() {
	        return archivo;
	    }

	    public void setArchivo(Archivo archivo) {
	        this.archivo = archivo;
	    }
	    
	    public void setRutaArchivo(String ruta){
	    	archivo.setRuta(ruta);
	    }

		public RepositorioEmpresas getEmpresas() {
			return empresas;
		}

		public void setEmpresas(RepositorioEmpresas empresas) {
			this.empresas = empresas;
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}

		public void actualizarEmpresas() {
			this.setEmpresas(new ManejadorDeArchivoEmpresas(archivo.getRuta()).getRepositorioEmpresas());
		}

	
	    
	
	
}
