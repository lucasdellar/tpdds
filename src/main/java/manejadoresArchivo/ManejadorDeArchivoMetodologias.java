package manejadoresArchivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import domain.ConversorFormatoArchivo;
import domain.IConversorFormatoArchivo;
import domain.Indicador;
import domain.Metodologia;
import domain.DomainExceptions.AgregarMetodologiaAlArchivoException;
import repositorios.RepositorioMetodologias;

public class ManejadorDeArchivoMetodologias {
	private File file;
	private RepositorioMetodologias repositorioMetodologias;
	private IConversorFormatoArchivo conversor;

	public ManejadorDeArchivoMetodologias(String rutaArchivo, IConversorFormatoArchivo conversor){
		file = new File(rutaArchivo);
		this.conversor = conversor;
		this.repositorioMetodologias = obtenerMetodologiasDeArchivo();
	}

	public ManejadorDeArchivoMetodologias(String rutaArchivo){
		this(rutaArchivo, new ConversorFormatoArchivo());
	} 

	private RepositorioMetodologias obtenerMetodologiasDeArchivo(){
		
		BufferedReader bufferedReader;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			String metodologiaLeida;
			RepositorioMetodologias repositorioMetodologiasDeArchivo = new RepositorioMetodologias();
			while((metodologiaLeida = bufferedReader.readLine()) != null){
				Metodologia miMetodologia = conversor.deFormatoArchivo(metodologiaLeida, Metodologia.class);
				repositorioMetodologiasDeArchivo.agregar(miMetodologia);
			}
			bufferedReader.close();
		
			return repositorioMetodologiasDeArchivo;
		
		} catch (IOException e) { throw new AgregarMetodologiaAlArchivoException("No fue posible leer las metodologias del archivo.");}
	}

	public File getArchivo(){
		return file;
	}

	public void agregarMetodologiaAlArchivo(Metodologia nuevaMetodologia){
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileWriter(file, true));
				
			printWriter.println(conversor.aFormatoArchivo(nuevaMetodologia));
			
			repositorioMetodologias.agregar(nuevaMetodologia);
			printWriter.close();
		} catch (IOException e) { throw new AgregarMetodologiaAlArchivoException("No se pudo guardar la metodologia en el archivo.");}
		
	}

	public void setRepositorioIndicadores(RepositorioMetodologias repositorioMetodologias) {
		this.repositorioMetodologias = repositorioMetodologias;
	}
 
	public RepositorioMetodologias getRepositorioMetodologias(){
		return obtenerMetodologiasDeArchivo();
	}
}
