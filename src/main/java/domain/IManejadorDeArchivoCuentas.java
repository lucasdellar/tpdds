package domain;

public interface IManejadorDeArchivoCuentas {
    void agregarCuentaAlArchivo(Cuenta nuevaCuenta);

    void setRepositorioCuentas(RepositorioCuentas repositorioCuentas);

    RepositorioCuentas getRepositorioCuentas();
}
