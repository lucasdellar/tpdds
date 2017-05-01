package domain;

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


	public ManejadorDeArchivoCuentas(String rutaArchivo, IConversorFormatoArchivo conversor) throws Exception{
		
		file = new File(rutaArchivo);
		
		if(!file.exists()){
			file.createNewFile();
		}

		this.conversor = conversor;
		this.repositorioCuentas = cuentasDeArchivo();
	}

	public ManejadorDeArchivoCuentas(String rutaArchivo) throws Exception{
		this(rutaArchivo, new ConversorFormatoArchivo());
	}

	private RepositorioCuentas cuentasDeArchivo() throws Exception{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String cuentaLeida;
		RepositorioCuentas repositorioCuentasDeArchivo = new RepositorioCuentas();

		while((cuentaLeida = bufferedReader.readLine()) != null){
			Cuenta miCuenta = conversor.DeFormatoArchivo(cuentaLeida, Cuenta.class);
			repositorioCuentasDeArchivo.agregarCuenta(miCuenta);
		}

		return repositorioCuentasDeArchivo;
	}
	
	public File getArchivo(){
		return file;
	}

	@Override
	public void agregarCuentaAlArchivo(Cuenta nuevaCuenta) throws IOException{
		PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
		printWriter.println(conversor.AFormatoArchivo(nuevaCuenta));
		repositorioCuentas.agregarCuenta(nuevaCuenta);
		printWriter.close();
	}


	@Override
	public void setRepositorioCuentas(RepositorioCuentas repositorioCuentas) {
		this.repositorioCuentas = repositorioCuentas;
	}
 
	@Override
	public RepositorioCuentas getRepositorioCuentas() throws IOException{
		return repositorioCuentas;
	}
}
