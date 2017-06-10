package manejadoresArchivo;

import domain.Empresa;
import repositorios.RepositorioEmpresas;

public interface IManejadorDeArchivoEmpresas {
    void agregarEmpresaAlArchivo(Empresa empresa);

    void setRepositorioCuentas(RepositorioEmpresas repositorioCuentas);

    RepositorioEmpresas getRepositorioEmpresas();

	void actualizarEmpresa(Empresa empresa);
}