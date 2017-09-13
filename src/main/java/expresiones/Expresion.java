package expresiones;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import domain.DomainExceptions.UnimplementedMethod;
import empresas.Empresa;

@DiscriminatorColumn(name = "Tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class Expresion{
	

	@Id@GeneratedValue
	long id;
	public abstract double calcular(Empresa empresa, String periodo);
	
}
