
package manejadoresArchivo;

import domain.ConversorFormatoArchivo;
import domain.IConversorFormatoArchivo;
import domain.Indicador;
import domain.DomainExceptions.AgregarIndicadorAlArchivoException;
import repositorios.Repositorio;

/*import domain.Indicadores.Modelo.Indicador;
import domain.Indicadores.Modelo.RepositorioIndicadores;
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import domain.DomainExceptions.AgregarIndicadorAlArchivoException;

public class ManejadorDeArchivoIndicadores {
	
	private File file;
	private Repositorio<Indicador> repositorioIndicadores;
	private IConversorFormatoArchivo conversor;


	public ManejadorDeArchivoIndicadores(String rutaArchivo, IConversorFormatoArchivo conversor){
		file = new File(rutaArchivo);
		this.conversor = conversor;
		this.repositorioIndicadores = obtenerIndicadoresDeArchivo();
	}

	public ManejadorDeArchivoIndicadores(String rutaArchivo){
		this(rutaArchivo, new ConversorFormatoArchivo());
	} 

	private Repositorio<Indicador> obtenerIndicadoresDeArchivo(){
		
		BufferedReader bufferedReader;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			String indicadorLeido;
			Repositorio<Indicador> repositorioIndicadoresDeArchivo = new Repositorio<Indicador>();
			while((indicadorLeido = bufferedReader.readLine()) != null){
				Indicador miIndicador = conversor.deFormatoArchivo(indicadorLeido, Indicador.class);
				repositorioIndicadoresDeArchivo.agregar(miIndicador);
			}
			bufferedReader.close();
		
			return repositorioIndicadoresDeArchivo;
		
		} catch (IOException e) { throw new AgregarIndicadorAlArchivoException("No fue posible leer los indicadores del archivo");}
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
		} catch (IOException e) { throw new AgregarIndicadorAlArchivoException("No se pudo guardar el indicador en el archivo.");}
		
	}

	public void setRepositorioIndicadores(Repositorio<Indicador> repositorioIndicadores) {
		this.repositorioIndicadores = repositorioIndicadores;
	}
 
	public Repositorio<Indicador> getRepositorioIndicadores(){
		return obtenerIndicadoresDeArchivo();
	}
	
}
