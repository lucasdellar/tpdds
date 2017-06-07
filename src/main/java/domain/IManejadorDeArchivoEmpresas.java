package domain;

public interface IManejadorDeArchivoEmpresas {
    void agregarEmpresaAlArchivo(Empresa empresa);

    void setRepositorioCuentas(RepositorioEmpresas repositorioCuentas);

    RepositorioEmpresas getRepositorioEmpresas();

	void actualizarEmpresa(Empresa empresa);
}