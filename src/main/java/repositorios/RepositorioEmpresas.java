package repositorios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import domain.DomainExceptions.AgregarCuentaAlArchivoException;
import empresas.Empresa;
import model.ConversorFormatoArchivo;
import model.IConversorFormatoArchivo;
import model.Indicador;
import model.Usuario;

public class RepositorioEmpresas extends Repositorio<Empresa> {
	
	private IConversorFormatoArchivo conversor;
	
	public RepositorioEmpresas(){}
	
	public RepositorioEmpresas(String file){
		conversor = new ConversorFormatoArchivo();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteria = builder.createQuery(Empresa.class);
		criteria.from(Empresa.class);
		this.setLista(manager.createQuery(criteria).getResultList());
		if(file != null ) this.traerEmpresas(file);
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
		
		} catch (IOException e) { throw new AgregarCuentaAlArchivoException("No se pudo leer las cuentas del archivo.");}
	}

	public boolean nombreYaUtilizado(String nombre) {
		return this.getLista().stream().anyMatch(x -> x.getNombre().equals(nombre));
	}
	
	public List<Empresa> getEmpresas(String nombre){
		  CriteriaBuilder criteriaBuilder = this.entityManager().getCriteriaBuilder();
		  CriteriaQuery<Empresa> criteriaQuery = criteriaBuilder.createQuery(Empresa.class);
		  Root<Empresa> root = criteriaQuery.from(Empresa.class);
		  criteriaQuery.select(root);
		  ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
		  criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), params));
		  TypedQuery<Empresa> query = this.entityManager().createQuery(criteriaQuery);
		  query.setParameter(params, nombre);
		  return query.getResultList();
	}
	
	public void traerTodas(){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteria = builder.createQuery(Empresa.class);
		criteria.from(Empresa.class);
		this.setLista(manager.createQuery(criteria).getResultList());
	}
	
}
