//package criterios;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import condiciones.Condicion;
//import condiciones.CondicionPrioritaria;
//import condiciones.CondicionTaxativa;
//import domain.Cuenta;
//import domain.Indicador;
//import domain.Valor;
//import domain.DomainExceptions.CriterioParaCondicionIncorrectaException;
//import empresas.Empresa;
//
//public class CrecimientoSiempre extends CriterioCrecimiento{
//
//	public CrecimientoSiempre(Valor valor, int principio, int fin) {
//		super(valor, principio, fin);
//	}
//
//	@Override
//	public Boolean aplicarTaxativa(Empresa unaEmpresa, CondicionTaxativa unaCondicion) {
//		List<Cuenta> cuentasDentroDelIntervalo = obtener_cuentasDentroDelIntervalo(unaEmpresa);
//		
//		return cuentasDentroDelIntervalo.stream().allMatch( x -> cumple(unaEmpresa, unaCondicion, x, cuentasDentroDelIntervalo));
//	}
//
//	@Override
//	public Boolean aplicarPrioritaria(Empresa unaEmpresa, Empresa otraEmpresa, CondicionPrioritaria unaCondicion) {
//		throw new CriterioParaCondicionIncorrectaException("No se puede utilizar este criterio para el tipo de condicion Prioritaria.");
//	}
//}
