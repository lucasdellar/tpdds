package repositorios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import domain.ConversorFormatoArchivo;
import domain.IConversorFormatoArchivo;
import domain.DomainExceptions.AgregarCuentaAlArchivoException;
import empresas.Empresa;

public class RepositorioEmpresas extends Repositorio<Empresa> {
	
	private IConversorFormatoArchivo conversor;
	
	public RepositorioEmpresas(String file){
		conversor = new ConversorFormatoArchivo();
		this.setLista(manager.createQuery("SELECT e FROM Empresa e").getResultList());
		this.traerEmpresas(file);
		
	}
	
	private void traerEmpresas(String file){
		
		List<Empresa> empresasDeArchivo = empresasDeArchivo(file);
		
		for(Empresa empresa : empresasDeArchivo){
			 if(!this.nombreYaUtilizado(empresa.getNombre()))
					 this.agregar(empresa);
		}
	}

	public List<Empresa> empresasDeArchivo(String file){
		
		BufferedReader bufferedReader;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			String cuentaLeida;
			List<Empresa> listaDeEmpresas = new ArrayList<Empresa>();
			while((cuentaLeida = bufferedReader.readLine()) != null){
				Empresa miEmpresa = conversor.deFormatoArchivo(cuentaLeida, Empresa.class);
				listaDeEmpresas.add(miEmpresa);
			}
			bufferedReader.close();
		
			return listaDeEmpresas;
		
		} catch (IOException e) { throw new AgregarCuentaAlArchivoException("No se pudo leer las cuentas del archivo");}
	}

	public boolean nombreYaUtilizado(String nombre) {
		return this.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre));
	}
	
}
