package expresiones;

import empresas.Empresa;
import model.Cuenta;
import repositorios.RepositorioIndicadores;

import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import domain.DomainExceptions.IndicadorInvalidoException;

@Entity
@Table(name="ExpresionesNoNumericas")
public class ExpresionNoNumerica extends Expresion{

	@Column(name = "nombreIdentificador")
	String nombreIdentificador;
	
	private ExpresionNoNumerica(){}
	
	public ExpresionNoNumerica(String identificador){
		this.nombreIdentificador= identificador;
	}
	
	public double calcular(Empresa unaEmpresa, String unPeriodo, RepositorioIndicadores repo){
		try {
			if(unaEmpresa.getCuentas().stream().anyMatch(unaCuenta -> esLaCuenta(unaCuenta, unPeriodo))){
			String valor = unaEmpresa.getCuentas().stream()
									 .filter(unaCuenta -> esLaCuenta(unaCuenta, unPeriodo))
									 .collect(Collectors.toList())
									 .get(0).getValor();
			return Double.parseDouble(valor);
			} else{
				return repo.getLista().stream()
						.filter( x -> x.getNombre().equals(nombreIdentificador))
						.collect(Collectors.toList())
						.get(0).aplicarIndicador(unPeriodo, unaEmpresa, repo);
			}
			} catch (IndexOutOfBoundsException e) {
				throw new IndicadorInvalidoException("Se esta utilizando un indicador sobre una empresa con datos insuficientes.");
			} catch (NullPointerException e){
				throw new IndicadorInvalidoException("Se esta utilizando un indicador sobre una empresa con datos insuficientes.");	
			}
	}
	
	Boolean esLaCuenta(Cuenta unaCuenta, String unPeriodo){
		return unaCuenta.getNombre().equals(nombreIdentificador) && unaCuenta.getPeriodo().equals(unPeriodo);
	}
	
}
