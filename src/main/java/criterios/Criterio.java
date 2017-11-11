package criterios;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import comparadores.Comparador;
import empresas.Empresa;
import model.Cuenta;
import model.Valor;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Criterio {
	
	@Id@GeneratedValue
	long id;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "valor")
	public Valor valor;
	
	protected Criterio(){}
	
	public Criterio(Valor valor) {
		this.valor = valor;
	}
	
	public Double actualizarPeriodo(Empresa unaEmpresa, String periodo){
		valor.setPeriodo(periodo);
		return valor.calcular(unaEmpresa);
	}
	
	public List<String> obtenerPeriodos(List<Cuenta> cuentas){
		String formula = valor.getIndicador().getFormula();
		List<Cuenta> cuentasValidas = cuentas.stream().filter(unaCuenta -> formula.contains(unaCuenta.getNombre())).collect(Collectors.toList());
		
		List<String> periodos = cuentasValidas.stream().map(unaCuenta -> unaCuenta.getPeriodo()).distinct().collect(Collectors.toList());

		return periodos;
	}
	
	public abstract double calcular(Empresa unaEmpresa);
	
	public abstract Boolean aplicar(Empresa unaEmpresa, double unValor, Comparador unComparador);
	
}
