package domain;

import domain.DomainExceptions.AgregarCuentaAlArchivoException;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ManejadorDeArchivoCuentas implements IManejadorDeArchivoCuentas {
	
	private File file;
	private RepositorioCuentas repositorioCuentas;
	private IConversorFormatoArchivo conversor;


	public ManejadorDeArchivoCuentas(String rutaArchivo, IConversorFormatoArchivo conversor){
		file = new File(rutaArchivo);
		this.conversor = conversor;
		this.repositorioCuentas = cuentasDeArchivo();
	}

	public ManejadorDeArchivoCuentas(String rutaArchivo){
		this(rutaArchivo, new ConversorFormatoArchivo());
	} 

	private RepositorioCuentas cuentasDeArchivo(){
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String cuentaLeida;
			RepositorioCuentas repositorioCuentasDeArchivo = new RepositorioCuentas();
			while((cuentaLeida = bufferedReader.readLine()) != null){
				Cuenta miCuenta = conversor.deFormatoArchivo(cuentaLeida);
				repositorioCuentasDeArchivo.agregarCuenta(miCuenta);
			}
			bufferedReader.close();
			return repositorioCuentasDeArchivo;
		} catch (IOException e) { throw new AgregarCuentaAlArchivoException("No se pudo leer las cuentas del archivo");}
	}
	
	public File getArchivo(){
		return file;
	}

	@Override
	public void agregarCuentaAlArchivo(Cuenta nuevaCuenta){
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileWriter(file, true));
			printWriter.println(conversor.aFormatoArchivo(nuevaCuenta));
			repositorioCuentas.agregarCuenta(nuevaCuenta);
			printWriter.close();
		} catch (IOException e) { throw new AgregarCuentaAlArchivoException("No se pudo agregar la cuenta al archivo");}
		
	}

	@Override
	public void setRepositorioCuentas(RepositorioCuentas repositorioCuentas) {
		this.repositorioCuentas = repositorioCuentas;
	}
 
	@Override
	public RepositorioCuentas getRepositorioCuentas(){
		return cuentasDeArchivo();
	}
}
