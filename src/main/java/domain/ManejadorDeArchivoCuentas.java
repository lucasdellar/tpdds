package domain;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ManejadorDeArchivoCuentas implements IManejadorDeArchivoCuentas {
	
	private File file;
	private Cuentas cuentas;
	private IConversorFormatoArchivo conversor;


	public ManejadorDeArchivoCuentas(String rutaArchivo, IConversorFormatoArchivo conversor) throws Exception{
		
		file = new File(rutaArchivo);
		
		if(!file.exists()){
			file.createNewFile();
		}

		this.conversor = conversor;
		this.cuentas = cuentasDeArchivo();
	}

	public ManejadorDeArchivoCuentas(String rutaArchivo) throws Exception{
		this(rutaArchivo, new ConversorFormatoArchivo());
	}

	private Cuentas cuentasDeArchivo() throws Exception{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String cuentaLeida;
		Cuentas cuentasDeArchivo = new Cuentas();

		while((cuentaLeida = bufferedReader.readLine()) != null){
			Cuenta miCuenta = conversor.DeFormatoArchivo(cuentaLeida, Cuenta.class);
			cuentasDeArchivo.agregarCuenta(miCuenta);
		}

		return cuentasDeArchivo;
	}
	
	public File getArchivo(){
		return file;
	}

	@Override
	public void agregarCuentaAlArchivo(Cuenta nuevaCuenta) throws IOException{
		PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
		printWriter.println(conversor.AFormatoArchivo(nuevaCuenta));
		cuentas.agregarCuenta(nuevaCuenta);
		printWriter.close();
	}


	@Override
	public void setCuentas(Cuentas cuentas) {
		this.cuentas = cuentas;
	}
 
	@Override
	public Cuentas getCuentas() throws IOException{
		return cuentas;
	}
}
