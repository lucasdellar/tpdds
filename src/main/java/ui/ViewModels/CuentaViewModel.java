package ui.ViewModels;

import org.uqbar.commons.utils.Observable;

import domain.*;
import scala.collection.generic.BitOperations.Int;

@Observable
public class CuentaViewModel {
    private Empresa empresa;
	private String nombre;
    private String periodo;
    private String valor;
    private ValidadorCuenta validador;

    private Archivo archivo;


    public CuentaViewModel() {
        validador = new ValidadorCuenta();
    }

    public CuentaViewModel(String nombre, String periodo, String valor, Archivo archivo) {
        this();
        this.nombre = nombre;
        this.periodo = periodo;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        validador.validarAnio(periodo);
        this.periodo = periodo;
    }

    public String getValor() {
        return valor;
    }

    public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		validador.validarEmpresa(empresa.getNombre());
		this.empresa = empresa;
	}

	public void setValor(String valor) {
        validador.validarValor(valor);
        this.valor = valor;
    }
    public void agregarCuenta() {
    	validador.validarQueNoEsteYaCargarda(nombre, periodo, empresa.getCuentas());
    	empresa.agregarCuenta(new Cuenta(nombre, periodo, valor));
        archivo.actualizarEmpresa(empresa);
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