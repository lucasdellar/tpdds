package domain;

import java.io.IOException;

/**
 * Created by Matias Fischer on 27/04/2017.
 */
public interface IManejadorDeArchivoCuentas {
    void agregarCuentaAlArchivo(Cuenta nuevaCuenta) throws IOException;

    void setRepositorioCuentas(RepositorioCuentas repositorioCuentas);

    RepositorioCuentas getRepositorioCuentas() throws IOException;
}
