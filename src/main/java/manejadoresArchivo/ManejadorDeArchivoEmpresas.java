package manejadoresArchivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import domain.ConversorFormatoArchivo;
import domain.Cuenta;
import domain.IConversorFormatoArchivo;
import domain.DomainExceptions.AgregarCuentaAlArchivoException;
import empresas.Empresa;
import repositorios.RepositorioEmpresas;

public class ManejadorDeArchivoEmpresas implements IManejadorDeArchivoEmpresas {
	
	private File file;
	private RepositorioEmpresas repositorioEmpresas;
//	private RepositorioCuentas repositorioCuentas;
	private IConversorFormatoArchivo conversor;


	public ManejadorDeArchivoEmpresas(String rutaArchivo, IConversorFormatoArchivo conversor){
		file = new File(rutaArchivo);
		this.conversor = conversor;
		this.repositorioEmpresas = empresasDeArchivo();
	}

	public ManejadorDeArchivoEmpresas(String rutaArchivo){
		this(rutaArchivo, new ConversorFormatoArchivo());
	} 

	private RepositorioEmpresas empresasDeArchivo(){
		
		BufferedReader bufferedReader;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			String cuentaLeida;
			RepositorioEmpresas repositorioCuentasDeArchivo = new RepositorioEmpresas();
			while((cuentaLeida = bufferedReader.readLine()) != null){
				Empresa miEmpresa = conversor.deFormatoArchivo(cuentaLeida, Empresa.class);
				repositorioCuentasDeArchivo.agregar(miEmpresa);
			}
			bufferedReader.close();
		
			return repositorioCuentasDeArchivo;
		
		} catch (IOException e) { throw new AgregarCuentaAlArchivoException("No se pudo leer las cuentas del archivo");}
	}

	public File getArchivo(){
		return file;
	}

	@Override
	public void agregarEmpresaAlArchivo(Empresa nuevaEmpresa){
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileWriter(file, true));
				
			printWriter.println(conversor.aFormatoArchivo(nuevaEmpresa));
			
			repositorioEmpresas.agregar(nuevaEmpresa);
			printWriter.close();
		} catch (IOException e) { throw new AgregarCuentaAlArchivoException("No se pudo agregar la cuenta al archivo");}
		
	}

	@Override
	public void setRepositorioCuentas(RepositorioEmpresas repositorioEmpresas) {
		this.repositorioEmpresas = repositorioEmpresas;
	}
 
	@Override
	public RepositorioEmpresas getRepositorioEmpresas(){
		return empresasDeArchivo();
	}

	public List<Cuenta> getCuentasDeEmpresa(String empresa) {
		
		for (Empresa unaEmpresa: repositorioEmpresas.getLista()) {
			if(unaEmpresa.getNombre().equals(empresa)) return unaEmpresa.getCuentas();
		}
		return null;
	}

	@Override
	public void actualizarEmpresa(Empresa empresa) {
		try {
			File tempFile = new File("myTempFile.txt");
	
			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(new FileWriter(tempFile, true));
	
			String nombreDeEmpresaARemover = empresa.getNombre();
			String currentLine;
	
			while((currentLine = reader.readLine()) != null) {
			    Empresa miEmpresa = conversor.deFormatoArchivo(currentLine, Empresa.class);
			    if(miEmpresa.getNombre().equals(nombreDeEmpresaARemover)) continue;
			    writer.println(conversor.aFormatoArchivo(miEmpresa));
			}
			writer.println(conversor.aFormatoArchivo(empresa));
			writer.close();
			reader.close(); 
			tempFile.renameTo(file);
			
		} catch (IOException e) {	throw new AgregarCuentaAlArchivoException("No se pudo agregar la cuenta al archivo con la empresa " + empresa.getNombre());} 
		
		
	}
	
}
