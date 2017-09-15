package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.Archivo;
import domain.Cuenta;
import empresas.Empresa;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import repositorios.Repositorio;
import repositorios.RepositorioEmpresas;
import validadores.ValidadorCuenta;
import validadores.ValidadorEmpresa;

@Observable

public class EmpresaViewModel {

	    private Empresa empresa;
	    private RepositorioEmpresas repoEmpresas;
	    private ValidadorEmpresa validador;

	    private Archivo archivo;

	    public EmpresaViewModel() {
	        validador = new ValidadorEmpresa();
	        
	    }

	    public EmpresaViewModel(Archivo archivo) {
	        this();
	        this.archivo = archivo;
	        repoEmpresas = new RepositorioEmpresas((archivo.getRuta()));
	    }

	    public String getNombreEmpresa() {
	        return empresa.getNombre();
	    }

	    public void setNombreEmpresa(String nombre) {
	        validador.validarNombre(nombre);
	        empresa.setNombre(nombre);
	    }

	    public void agregarEmpresa() {
	    	validador.validarQueNoEsteYaCargarda(empresa.getNombre(), repoEmpresas);
	        repoEmpresas.agregar(empresa);
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
			return repoEmpresas;
		}

		public void setEmpresas(RepositorioEmpresas empresas) {
			this.repoEmpresas = empresas;
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}

		public void actualizarEmpresas() {
			this.setEmpresas(new RepositorioEmpresas(archivo.getRuta()));
		}
	
}
