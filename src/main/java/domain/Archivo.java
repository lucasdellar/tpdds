package domain;

import domain.DomainExceptions.ArchivoInvalidoException;
import manejadoresArchivo.IManejadorDeArchivoEmpresas;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;

import java.io.File;


public class Archivo {
	private String ruta;
    private IManejadorDeArchivoEmpresas manejador;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        validarRutaArchivo(ruta);
        this.ruta = ruta;
        manejador = new ManejadorDeArchivoEmpresas(ruta);
    }

    public static void validarRutaArchivo(String rutaArchivo) {
        String archRegex = ".+\\.txt";
        if (rutaArchivo == null  || rutaArchivo == ""|| !rutaArchivo.matches(archRegex)) throw new ArchivoInvalidoException("El nombre del archivo no es valido. Debe ser un .txt");
        if (!new File(rutaArchivo).exists()) throw new ArchivoInvalidoException("El archivo ingresado no existe. Ingrese otro nombre o creelo y vuelva a intentarlo.");
    }

    public IManejadorDeArchivoEmpresas getManejador() {
        return manejador;
    }

    public void setManejador(IManejadorDeArchivoEmpresas manejador) {
        this.manejador = manejador;
    }

    public void agregarEmpresa(Empresa empresa){
        this.manejador.agregarEmpresaAlArchivo(empresa);
    }

	public void actualizarEmpresa(Empresa empresa) {
		this.manejador.actualizarEmpresa(empresa);
	}
}
