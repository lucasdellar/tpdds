package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import empresas.Empresa;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Valor {
	
	@Id@GeneratedValue
	long id;

	public abstract Double calcular(Empresa unaEmpresa);

	public abstract void setPeriodo(String periodo);

	public abstract Indicador getIndicador();

}
