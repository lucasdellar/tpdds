package ui;

import domain.Cuenta;
import domain.DomainExceptions.ArchivoInvalidoException;
import domain.IManejadorDeArchivoCuentas;
import domain.ManejadorDeArchivoCuentas;

import java.io.File;

/**
 * Created by Matias Fischer on 07/05/2017.
 */
public class Archivo implements IArchivo {
    private String ruta;
    private IManejadorDeArchivoCuentas manejador;

    @Override
    public String getRuta() {
        return ruta;
    }

    @Override
    public void setRuta(String ruta) {
        validarRutaArchivo(ruta);

        this.ruta = ruta;
        manejador = new ManejadorDeArchivoCuentas(ruta);
    }

    @Override
    public void validarRutaArchivo(String rutaArchivo) {
        String archRegex = ".+\\.txt";
        if (rutaArchivo == null  || rutaArchivo == ""|| !rutaArchivo.matches(archRegex)) throw new ArchivoInvalidoException("El nombre del archivo no es valido. Debe ser un .txt");
        if (!new File(rutaArchivo).exists()) throw new ArchivoInvalidoException("El archivo ingresado no existe. Ingrese otro nombre o creelo y vuelva a intentarlo.");
    }

    @Override
    public IManejadorDeArchivoCuentas getManejador() {
        return manejador;
    }

    @Override
    public void setManejador(IManejadorDeArchivoCuentas manejador) {
        this.manejador = manejador;
    }

    public void agregarCuenta(Cuenta cuenta){
        this.manejador.agregarCuentaAlArchivo(cuenta);
    }
}
