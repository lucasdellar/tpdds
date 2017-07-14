package criterios;

import java.util.List;

import condiciones.Condicion;
import domain.Cuenta;
import domain.Indicador;
import empresas.Empresa;

public abstract class CriterioCrecimiento extends Criterio {
	
public CriterioCrecimiento(Indicador indicador) {
		super(indicador);
	}


public Boolean cumple(Empresa unaEmpresa, Condicion unaCondicion, Cuenta x, List<Cuenta> cuentasDentroDelIntervalo) {
		int posicion = cuentasDentroDelIntervalo.indexOf(x);
		return posicion == cuentasDentroDelIntervalo.size() - 1 || unaCondicion.getComparador()
				.comparar(getIndicador().aplicarIndicador(x.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()),
						getIndicador().aplicarIndicador(cuentasDentroDelIntervalo.get(posicion + 1)
								.getPeriodo(), unaEmpresa, unaCondicion.getRepoIndicadores()));	
	}

}
