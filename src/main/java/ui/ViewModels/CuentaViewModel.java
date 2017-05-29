package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.*;

@Observable
public class CuentaViewModel {
    private String empresa;
	private String nombre;
    private String anio;
    private String valor;
    private ValidadorCuenta validador;

    private Archivo archivo;


    public CuentaViewModel() {
        validador = new ValidadorCuenta();
    }

    public CuentaViewModel(String nombre, String anio, String valor, Archivo archivo) {
        this();
        this.nombre = nombre;
        this.anio = anio;
        this.valor = valor;
        this.archivo = archivo;
    }


    public CuentaViewModel(Archivo archivo) {
        this();
        this.archivo = archivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validador.validarNombre(nombre);
        this.nombre = nombre;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        validador.validarAnio(anio);
        this.anio = anio;
    }

    public String getValor() {
        return valor;
    }

    public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		validador.validarEmpresa(empresa);
		this.empresa = empresa;
	}

	public void setValor(String valor) {
        validador.validarValor(valor);
        this.valor = valor;
    }
    public void agregarCuenta() {
    	validador.validarQueNoEsteYaCargarda(nombre, new ManejadorDeArchivoCuentas(archivo.getRuta()).getRepositorioCuentas());
        archivo.agregarCuenta(new Cuenta(this));
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
    

}