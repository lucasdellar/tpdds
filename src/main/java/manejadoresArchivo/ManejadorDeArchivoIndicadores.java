
package manejadoresArchivo;

import domain.DomainExceptions.AgregarMetodologiaAlArchivoException;
import model.ConversorFormatoArchivo;
import model.IConversorFormatoArchivo;
import model.Indicador;
import repositorios.RepositorioIndicadores;

/*import domain.Indicadores.Modelo.Indicador;
import domain.Indicadores.Modelo.RepositorioIndicadores;
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import domain.DomainExceptions.AgregarMetodologiaAlArchivoException;

public class ManejadorDeArchivoIndicadores {
	
	private File file;
	private RepositorioIndicadores repositorioIndicadores;
	private IConversorFormatoArchivo conversor;


	public ManejadorDeArchivoIndicadores(String rutaArchivo, IConversorFormatoArchivo conversor){
		file = new File(rutaArchivo);
		this.conversor = conversor;
		this.repositorioIndicadores = obtenerIndicadoresDeArchivo();
	}

	public ManejadorDeArchivoIndicadores(String rutaArchivo){
		this(rutaArchivo, new ConversorFormatoArchivo());
	} 

	private RepositorioIndicadores obtenerIndicadoresDeArchivo(){
		
		BufferedReader bufferedReader;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			String indicadorLeido;
			RepositorioIndicadores repositorioIndicadoresDeArchivo = new RepositorioIndicadores();
			while((indicadorLeido = bufferedReader.readLine()) != null){
				Indicador miIndicador = conversor.deFormatoArchivo(indicadorLeido, Indicador.class);
				repositorioIndicadoresDeArchivo.agregar(miIndicador);
			}
			bufferedReader.close();
		
			return repositorioIndicadoresDeArchivo;
		
		} catch (IOException e) { throw new AgregarMetodologiaAlArchivoException("No fue posible leer los indicadores del archivo");}
	}

	public File getArchivo(){
		return file;
	}

	public void agregarIndicadorAlArchivo(Indicador nuevoIndicador){
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileWriter(file, true));
				
			printWriter.println(conversor.aFormatoArchivo(nuevoIndicador));
			
			repositorioIndicadores.agregar(nuevoIndicador);
			printWriter.close();
		} catch (IOException e) { throw new AgregarMetodologiaAlArchivoException("No se pudo guardar el indicador en el archivo.");}
		
	}

	public void setRepositorioIndicadores(RepositorioIndicadores repositorioIndicadores) {
		this.repositorioIndicadores = repositorioIndicadores;
	}
 
	public RepositorioIndicadores getRepositorioIndicadores(){
		return obtenerIndicadoresDeArchivo();
	}
	
}
