package domain;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ManejadorDeArchivoCuentas {
	
	File file;
	Cuentas cuentas;
	
	public ManejadorDeArchivoCuentas(String rutaArchivo) throws IOException{
		
		file = new File(rutaArchivo);
		
		if(!file.exists()){
			file.createNewFile();
		}
	}
	
	public Cuentas getCuentas() throws IOException{

		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String cuentaLeida;
		cuentas = new Cuentas();

		while((cuentaLeida = bufferedReader.readLine()) != null){
			Cuenta miCuenta = Cuenta.fromJson(cuentaLeida);
			cuentas.agregarCuenta(miCuenta);
		}
		
		return cuentas;
	}
	
	public void agregarCuentaAlArchivo(Cuenta nuevaCuenta) throws IOException{
		PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
		printWriter.println(nuevaCuenta.toJson());
		printWriter.close();
	}
	
	
}
