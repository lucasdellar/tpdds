package main;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import comparadores.Comparador;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import criterios.Crecimiento;
import criterios.Sumatoria;
import empresas.Empresa;
import model.Cuenta;
import model.Indicador;
import model.IndicadorPorEmpresa;
import model.Metodologia;
import model.Usuario;
import model.Valor;
import model.ValorIndicador;
import repositorios.RepositorioIndicadores;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {
    	
    	Usuario user = new Usuario("admin", "utnso");
    	persist(user);
    	
    	Empresa coke = new Empresa("Coca Cola");
    	coke.setCuentas(new ArrayList<Cuenta>());
    	coke.agregarCuenta(new Cuenta("Fanta", "2017", "300000"));
    	coke.agregarCuenta(new Cuenta("7 arriba", "2010", "104400"));
    	coke.agregarCuenta(new Cuenta("Agua", "2020", "77586124"));
    	
    	Empresa globant = new Empresa("Globant");
    	globant.setCuentas(new ArrayList<Cuenta>());
    	globant.agregarCuenta(new Cuenta("Patrimonio Neto", "2015", "0"));
    	globant.agregarCuenta(new Cuenta("ROE", "2140", "1"));
    	globant.agregarCuenta(new Cuenta("Humanos", "2010", "666"));
    	
    	Indicador unIndicador = new Indicador("simple", "5+5", "admin");
    	Indicador otroIndicador = new Indicador("middle", "10-20", "admin");
    	Indicador tercerIndicador = new Indicador("heavy", "simple+middle", "admin");
    	Indicador roe = new Indicador("ROE", "ROA+10", "admin");
    	Indicador roa = new Indicador("ROA", "20", "admin");
    	Indicador monstruo = new Indicador("monstruo", "ROA+ROE-middle*simple", "admin");
    	
    	persist(globant);
    	persist(coke);
    	persist(unIndicador);
    	persist(otroIndicador);
    	persist(tercerIndicador);
    	persist(roe);
    	persist(roa);
    	persist(monstruo);
    	
    	List<Empresa> masEmpresas = this.obtenerEmpresasParaMetodologia();
    	for(Empresa empresa : masEmpresas){
    		persist(empresa);
    	}
    	
    	IndicadorPorEmpresa unIndicadorPorEmpresa = new IndicadorPorEmpresa(coke.getId(), unIndicador.getId(), "2015", unIndicador.aplicarIndicador("2015", coke, new RepositorioIndicadores("admin")));
    	
    	persist(unIndicadorPorEmpresa);
    	
    	/* METODOLOGIA */
    	RepositorioIndicadores repositorio = new RepositorioIndicadores();
		Indicador indicador1 = new Indicador("indicadorTestA", "testCuentaA * 5");
		Indicador indicador2 = new Indicador("indicadorTestB", "testCuentaB + 25");
		Indicador indicador3  = new Indicador("indicadorTestC", "testCuentaC / 2");
		repositorio.agregar(indicador1);
		repositorio.agregar(indicador2);
		repositorio.agregar(indicador3);
		CondicionTaxativa taxativa1 = new CondicionTaxativa(repositorio, Comparador.MAYOR, 20);
		CondicionTaxativa taxativa2 = new CondicionTaxativa(repositorio, Comparador.MAYOR);
		CondicionTaxativa taxativa3 = new CondicionTaxativa(repositorio, Comparador.MAYOR);
		List<CondicionTaxativa> condicionesTaxativas = new ArrayList<>();
		List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<>();
		condicionesTaxativas.add(taxativa1);
		condicionesTaxativas.add(taxativa2);
		condicionesTaxativas.add(taxativa3);
		
		Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativas, condicionesPrioritarias);
		
		Valor valorUno = new ValorIndicador(indicador1.getNombre(), taxativa1.getRepoIndicadores());
		Valor valorDos = new ValorIndicador(indicador2.getNombre(), taxativa2.getRepoIndicadores());
		Valor valorTres = new ValorIndicador(indicador3.getNombre(), taxativa3.getRepoIndicadores());
		taxativa1.setCriterio(new Sumatoria(valorUno));
		taxativa2.setCriterio(new Crecimiento(valorDos, 2015, 2017, 1));
		taxativa3.setCriterio(new Crecimiento(valorTres, 2015, 2017, 0));
    	
		/* FIN */
		persist(metodologia);
    	
    });
  }
  
  private List<Empresa> obtenerEmpresasParaMetodologia(){
		Empresa empresa1 = new Empresa("Tisla");
		empresa1.setCuentas(new ArrayList<>());
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 75
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));

		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "20")); //CrecimientoCasiSiempre: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "25"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //CrecimientoSiempre: 9.15
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
		
		Empresa empresa2 = new Empresa("EatBa");
		empresa2.setCuentas(new ArrayList<>());
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 30
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "4"));
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "5")); //Promedio: 5
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "6"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));

		Empresa empresa3 = new Empresa("Ginus");
		empresa3.setCuentas(new ArrayList<>());
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 150
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
		
		List<Empresa> empresas = new ArrayList<>();
		empresas.add(empresa1);
		empresas.add(empresa2);
		empresas.add(empresa3);
		
		return empresas;
  }

}
