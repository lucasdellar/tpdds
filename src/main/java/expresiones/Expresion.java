package expresiones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import empresas.Empresa;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Expresion{
	
	@Id@GeneratedValue(strategy=GenerationType.TABLE)
	long id;
	public abstract double calcular(Empresa empresa, String periodo);
	
}
