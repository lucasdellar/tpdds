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
		this.cuentas = CargarCuentasDeArchivo();
	}

	public ManejadorDeArchivoCuentas(String rutaArchivo) throws Exception{
		this(rutaArchivo, new ConversorFormatoArchivo());
	}

	private Cuentas CargarCuentasDeArchivo() throws Exception{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String cuentaLeida;
		Cuentas cuentasDeArchivo = new Cuentas();

		while((cuentaLeida = bufferedReader.readLine()) != null){
			Cuenta miCuenta = conversor.DeFormatoArchivo(cuentaLeida, Cuenta.class);
			cuentasDeArchivo.agregarCuenta(miCuenta);
		}

		return cuentasDeArchivo;
	}

	@Override
	public void agregarCuentaAlArchivo(Cuenta nuevaCuenta) throws IOException{
		PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
		printWriter.println(conversor.AFormatoArchivo(nuevaCuenta));
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
