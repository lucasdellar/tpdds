package ui;

import domain.Cuenta;
import domain.IManejadorDeArchivoCuentas;

/**
 * Created by Matias Fischer on 07/05/2017.
 */
public interface IArchivo {
    String getRuta();

    void setRuta(String ruta);

    void validarRutaArchivo(String rutaArchivo);

    IManejadorDeArchivoCuentas getManejador();

    void setManejador(IManejadorDeArchivoCuentas manejador);
    void agregarCuenta(Cuenta cuenta);
}
