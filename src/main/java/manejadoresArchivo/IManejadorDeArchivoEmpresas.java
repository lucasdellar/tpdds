package manejadoresArchivo;

import domain.Empresa;
import repositorios.Repositorio;

public interface IManejadorDeArchivoEmpresas {
    void agregarEmpresaAlArchivo(Empresa empresa);

    void setRepositorioCuentas(Repositorio<Empresa> repositorioCuentas);

    Repositorio<Empresa> getRepositorioEmpresas();

	void actualizarEmpresa(Empresa empresa);
}