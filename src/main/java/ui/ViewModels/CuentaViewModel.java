package ui.ViewModels;

import domain.*;
import ui.IArchivo;

/**
 * Created by Matias Fischer on 07/05/2017.
 */

public class CuentaViewModel {
    private String nombre;
    private String anio;
    private String patrimonio_neto;
    private ValidadorCuenta validador;



    private IArchivo archivo;


    public CuentaViewModel() {
        validador = new ValidadorCuenta();
    }

    public CuentaViewModel(String nombre, String anio, String patrimonio_neto, IArchivo archivo) {
        this();
        this.nombre = nombre;
        this.anio = anio;
        this.patrimonio_neto = patrimonio_neto;
        this.archivo = archivo;
    }


    public CuentaViewModel(IArchivo archivo) {
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

    public String getPatrimonio_neto() {
        return patrimonio_neto;
    }

    public void setPatrimonio_neto(String patrimonio_neto) {
        validador.validarPatrimonio(patrimonio_neto);

        this.patrimonio_neto = patrimonio_neto;
    }
    public void agregarCuenta() {
        archivo.agregarCuenta(new Cuenta(this));
    }

    public IArchivo getArchivo() {
        return archivo;
    }

    public void setArchivo(IArchivo archivo) {
        this.archivo = archivo;
    }

}