import domain.*;

import domain.DomainExceptions.*;
import domain.Metodologia;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import parser.*;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioIndicadores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import OperacionesMatematicas.ResolutorDeCuentas;
import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.ComparaEmpresasPorIndicador;
import condiciones.Condicion;
import condiciones.CondicionTaxativa;
import condiciones.CondicionPrioritaria;
import criterios.CrecimientoCasiSiempre;
import criterios.CrecimientoSiempre;
import criterios.CriterioCrecimiento;
import criterios.Promedio;
import criterios.Sumatoria;
import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.InviertiendoViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.AgregarEmpresaViewModel;

public class InvirtiendoTest {

    IConversorFormatoArchivo conversor;
    ManejadorDeArchivoEmpresas manejador;
    InviertiendoViewModel inviertiendoViewModel;
    CuentaViewModel cuentasViewModel;
    EmpresaViewModel empresaViewModel;
    AgregarEmpresaViewModel agregarEmpresaViewModel;
    File file;
	
	@Before
	public void initObjects() throws IOException{ 
		file = new File("cuentasMock.txt");
		file.createNewFile();
        conversor = new ConversorFormatoArchivo();
        manejador = new ManejadorDeArchivoEmpresas("cuentasMock.txt");
        inviertiendoViewModel = new InviertiendoViewModel();
        cuentasViewModel = new CuentaViewModel();
        empresaViewModel = new EmpresaViewModel();
        Archivo archivo = new Archivo();
        archivo.setRuta("cuentasMock.txt");
        agregarEmpresaViewModel = new AgregarEmpresaViewModel(archivo);
        cuentasViewModel.setArchivo(archivo);
        empresaViewModel.setArchivo(archivo);
	}
	
	//  *** TEST's Entrega: METODOLOGÍAS ***
	
	@Test
	public void comparadorMenor(){
		ComparadorMenor comparador = new ComparadorMenor();
		Assert.assertEquals(comparador.comparar(5, 9), true);
	}
	
	@Test
	public void comparadorMayor(){
		ComparadorMayor comparador = new ComparadorMayor();
		Assert.assertEquals(comparador.comparar(5, 9), false);
	}
	
	@Test
	public void aplicar_Taxativa_unitariamente(){
		/* Objetivo: aplicar taxativa a una sola empresa (lista con un elemento).
		 * Resultado: que devuelva la lista con dicha empresa. 
		 */
		RepositorioIndicadores repo = new RepositorioIndicadores();
		CondicionTaxativa taxativa = new CondicionTaxativa(repo, new ComparadorMenor(), 100);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		taxativa.setCriterio(new Promedio(unIndicador));
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));	
		
		List<EmpresaRankeada> empresas = new ArrayList<>();
		empresas.add(miEmpresa);
		Assert.assertEquals(taxativa.aplicar(empresas).get(0).getNombre(), miEmpresa.getNombre());
	}
	
	@Test
	public void aplicar_Taxativa_varias_empresas(){
		/* Objetivo: filtrar las empresas según la taxativa. La primera empresa
		 * cumple la condicion, la siguiente no. 
		 * Resultado: una lista con la única empresa que cumple la condición.
		 */
		RepositorioIndicadores repo = new RepositorioIndicadores();
		CondicionTaxativa taxativa = new CondicionTaxativa(repo, new ComparadorMenor(), 100);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		taxativa.setCriterio(new Promedio(unIndicador));
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));	
		
		EmpresaRankeada otraEmpresa = new EmpresaRankeada("otraEmpresa");
		otraEmpresa.setCuentas(new ArrayList<>());
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3000000"));
		
		List<EmpresaRankeada> empresas = new ArrayList<>();
		empresas.add(miEmpresa);
		empresas.add(otraEmpresa);
		Assert.assertEquals(taxativa.aplicar(empresas).size(), 1);
	}
	
	@Test
	public void aplicar_Prioritaria_varias_empresas(){
		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
		 * en primer lugar.
		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
		 */
		
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta * 5");
		CondicionPrioritaria prioritaria = new CondicionPrioritaria(repositorio, new ComparadorMayor(), 2);
		repositorio.agregar(unIndicador);
		prioritaria.setCriterio(new Sumatoria(unIndicador));
		
		EmpresaRankeada empresa1 = new EmpresaRankeada("testEmpresa1");
		empresa1.setCuentas(new ArrayList<>());
		empresa1.agregarCuenta(new Cuenta("testCuenta", "2015", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuenta", "2016", "5")); //75
		empresa1.agregarCuenta(new Cuenta("testCuenta", "2017", "5"));
		
		EmpresaRankeada empresa2 = new EmpresaRankeada("testEmpresa2");
		empresa2.setCuentas(new ArrayList<>());
		empresa2.agregarCuenta(new Cuenta("testCuenta", "2014", "3"));
		empresa2.agregarCuenta(new Cuenta("testCuenta", "2015", "4")); //60
		empresa2.agregarCuenta(new Cuenta("testCuenta", "2016", "5"));
		
		EmpresaRankeada empresa3 = new EmpresaRankeada("testEmpresa3");
		empresa3.setCuentas(new ArrayList<>());
		empresa3.agregarCuenta(new Cuenta("testCuenta", "2013", "7"));
		empresa3.agregarCuenta(new Cuenta("testCuenta", "2014", "5")); //65
		empresa3.agregarCuenta(new Cuenta("testCuenta", "2015", "1"));
		
		List<EmpresaRankeada> empresas = new ArrayList<>();
		empresas.add(empresa1);
		empresas.add(empresa2);
		empresas.add(empresa3);
		
		Assert.assertEquals(prioritaria.aplicar(empresas).get(0).getNombre(), "testEmpresa1");
	}
	
	@Test
	public void metodología_solo_prioritarias(){
		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
		 * en primer lugar.
		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
		 */
		
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
		CondicionPrioritaria prioritaria1 = new CondicionPrioritaria(repositorio, new ComparadorMayor(), 1);
		CondicionPrioritaria prioritaria2 = new CondicionPrioritaria(repositorio, new ComparadorMenor(), 3);
		CondicionPrioritaria prioritaria3 = new CondicionPrioritaria(repositorio, new ComparadorMayor(), 5);
		List<Condicion> condicionesPrioritarias = new ArrayList<>();
		condicionesPrioritarias.add(prioritaria1);
		condicionesPrioritarias.add(prioritaria2);
		condicionesPrioritarias.add(prioritaria3);
		Metodologia metodologia = new Metodologia("testMetodologia", condicionesPrioritarias);
		
		repositorio.agregar(unIndicador);
		prioritaria1.setCriterio(new Sumatoria(unIndicador));
		prioritaria2.setCriterio(new Promedio(otroIndicador));
		prioritaria3.setCriterio(new Promedio(tercerIndicador));
		
		Empresa empresa1 = new EmpresaRankeada("testEmpresa1");
		empresa1.setCuentas(new ArrayList<>());
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "15")); //Promedio: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "15"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //Promedio: 9.15
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
		
		Empresa empresa2 = new EmpresaRankeada("testEmpresa2");
		empresa2.setCuentas(new ArrayList<>());
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 6
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "4"));
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "5")); //Promedio: 5
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "6"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));
		
		Empresa empresa3 = new EmpresaRankeada("testEmpresa3");
		empresa3.setCuentas(new ArrayList<>());
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 30
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
		
		/* prioritaria1, peso 1 = empresa3, empresa2, empresa1 => Rankings E1 E2 E3: 1*(3-2), 1*(3-1), 1*(3-0).
		 * prioritaria2, peso 3 = empresa2, empresa3, empresa1 => Rankings E1 E2 E3: 3*(3-2), 3*(3-0) ,3*(3-1).
		 * prioritaria3, peso 5 = empresa3, empresa1, empresa2 => Rankings E1 E2 E3: 5*(3-1), 5*(3-2), 5*(3-0).
		 * Rankings E1, E2, E3 = 14, 16, 24 => empresa3, empresa2, empresa1.
		 */
		
		List<Empresa> empresas = new ArrayList<>();
		empresas.add(empresa1);
		empresas.add(empresa2);
		empresas.add(empresa3);
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(0).getNombre(), "testEmpresa3");
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(1).getNombre(), "testEmpresa2");
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(2).getNombre(), "testEmpresa1");
	}
	
	@Test
	public void metodología_solo_taxativas(){
		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
		 * en primer lugar.
		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
		 */
		
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
		CondicionTaxativa taxativa1 = new CondicionTaxativa(repositorio, new ComparadorMayor(), 20);
		CondicionTaxativa taxativa2 = new CondicionTaxativa(repositorio, new ComparadorMayor());
		CondicionTaxativa taxativa3 = new CondicionTaxativa(repositorio, new ComparadorMayor());
		List<Condicion> condicionesTaxativas = new ArrayList<>();
		condicionesTaxativas.add(taxativa1);
		condicionesTaxativas.add(taxativa2);
		condicionesTaxativas.add(taxativa3);
		Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativas);
		
		repositorio.agregar(unIndicador);
		taxativa1.setCriterio(new Sumatoria(unIndicador));
		taxativa2.setCriterio(new CrecimientoCasiSiempre(otroIndicador));
		taxativa3.setCriterio(new CrecimientoSiempre(tercerIndicador, 2015, 2017, 1));
		
		Empresa empresa1 = new EmpresaRankeada("testEmpresa1");
		empresa1.setCuentas(new ArrayList<>());
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "20")); //CrecimientoCasiSiempre: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "25"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //CrecimientoSiempre: 9.15
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
		
		Empresa empresa2 = new EmpresaRankeada("testEmpresa2");
		empresa2.setCuentas(new ArrayList<>());
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 6
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "4"));
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "5")); //Promedio: 5
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "6"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));
		
		Empresa empresa3 = new EmpresaRankeada("testEmpresa3");
		empresa3.setCuentas(new ArrayList<>());
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 30
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
		
		/* prioritaria1, peso 1 = empresa3, empresa2, empresa1 => Rankings E1 E2 E3: 1*(3-2), 1*(3-1), 1*(3-0).
		 * prioritaria2, peso 3 = empresa2, empresa3, empresa1 => Rankings E1 E2 E3: 3*(3-2), 3*(3-0) ,3*(3-1).
		 * prioritaria3, peso 5 = empresa3, empresa1, empresa2 => Rankings E1 E2 E3: 5*(3-1), 5*(3-2), 5*(3-0).
		 * Rankings E1, E2, E3 = 14, 16, 24 => empresa3, empresa2, empresa1.
		 */
		
		List<Empresa> empresas = new ArrayList<>();
		empresas.add(empresa1);
		empresas.add(empresa2);
		empresas.add(empresa3);
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(0).getNombre(), "testEmpresa3");
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(1).getNombre(), null);
	}
	
	@Test
	public void metodología_taxativas_prioritarias(){
		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
		 * en primer lugar.
		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
		 */
		
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
		condicionesTaxativas taxativa1 = new condicionesTaxativas(repositorio, new ComparadorMayor(), 1);
		condicionesTaxativas taxativa2 = new condicionesTaxativas(repositorio, new ComparadorMenor(), 3);
		condicionesTaxativas taxativa3 = new condicionesTaxativas(repositorio, new ComparadorMayor(), 5);
		List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<>();
		condicionesTaxativas.add(taxativa1);
		condicionesTaxativas.add(taxativa2);
		condicionesTaxativas.add(taxativa3);
		Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativas);
		
		repositorio.agregar(unIndicador);
		taxativa1.setCriterio(new Sumatoria(unIndicador));
		taxativa2.setCriterio(new CrecimientoCasiSiempre(otroIndicador));
		taxativa3.setCriterio(new CrecimientoSiempre(tercerIndicador));
		
		Empresa empresa1 = new EmpresaRankeada("testEmpresa1");
		empresa1.setCuentas(new ArrayList<>());
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "15")); //Promedio: 15
		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "15"));
		
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //Promedio: 9.15
		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
		
		Empresa empresa2 = new EmpresaRankeada("testEmpresa2");
		empresa2.setCuentas(new ArrayList<>());
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 6
		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "4"));
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "5")); //Promedio: 5
		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "6"));
		
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));
		
		Empresa empresa3 = new EmpresaRankeada("testEmpresa3");
		empresa3.setCuentas(new ArrayList<>());
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 30
		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
		
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
		
		/* prioritaria1, peso 1 = empresa3, empresa2, empresa1 => Rankings E1 E2 E3: 1*(3-2), 1*(3-1), 1*(3-0).
		 * prioritaria2, peso 3 = empresa2, empresa3, empresa1 => Rankings E1 E2 E3: 3*(3-2), 3*(3-0) ,3*(3-1).
		 * prioritaria3, peso 5 = empresa3, empresa1, empresa2 => Rankings E1 E2 E3: 5*(3-1), 5*(3-2), 5*(3-0).
		 * Rankings E1, E2, E3 = 14, 16, 24 => empresa3, empresa2, empresa1.
		 */
		
		List<Empresa> empresas = new ArrayList<>();
		empresas.add(empresa1);
		empresas.add(empresa2);
		empresas.add(empresa3);
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(0).getNombre(), "testEmpresa3");
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(1).getNombre(), "testEmpresa2");
		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(2).getNombre(), "testEmpresa1");
	}
	
	@Test
	public void agregar_condicion_Taxativa(){
		CondicionTaxativa crece = new CondicionTaxativa(new RepositorioIndicadores(), new ComparadorMayor());
		List<Condicion> lista = new ArrayList<>();
		lista.add(crece);
		Metodologia metodologia = new Metodologia("testMetodologia", lista);
		Assert.assertEquals(metodologia.getCondiciones().size(), 1);
	}
	
	@Test
	public void metodologiaOrdenaCorrectamentePorPesoLasEmpresas(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa1");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.aumentarRanking(10);
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		EmpresaRankeada miEmpresa2 = new EmpresaRankeada("testEmpresa2");
		miEmpresa2.setCuentas(new ArrayList<>());
		miEmpresa2.aumentarRanking(15);
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		EmpresaRankeada miEmpresa3 = new EmpresaRankeada("testEmpresa3");
		miEmpresa3.setCuentas(new ArrayList<>());
		miEmpresa3.aumentarRanking(5);
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<>();
		empresas.add(miEmpresa);
		empresas.add(miEmpresa2);
		empresas.add(miEmpresa3);
		Metodologia metodologiaTest = new Metodologia("testMetodologia", new ArrayList<>());
		metodologiaTest.ordenarPorRanking(empresas);
		Assert.assertEquals(empresas.get(0).getNombre(), "testEmpresa3");
		Assert.assertEquals(empresas.get(1).getNombre(), "testEmpresa1");
		Assert.assertEquals(empresas.get(2).getNombre(), "testEmpresa2");
	}

	@Test
	public void condicionCrecimientoEvaluaCorrectamenteUnaEmpresa(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		CondicionTaxativa condicion = new CondicionTaxativa(repo, new ComparadorMenor());
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		condicion.setCriterio(new CrecimientoSiempre(unIndicador, 2015, 2017));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));
		empresas.add(miEmpresa);		
		Assert.assertEquals(condicion.aplicar(empresas).size(), 1);
	}	
	
	//  *** TEST's PREVIOS ***
	
	@Test
    public void verificarFormatoParser(){
		parser.Parser.verificarFormato("27");
	}
	
	@Test
    public void verificarFormatoParserConPalabra(){
		parser.Parser.verificarFormato(" otro + aasd + 5 / as");
	}
	
	@Test(expected = ParserException.class)
    public void parserExceptionPorFormato(){
		parser.Parser.verificarFormato("aa s27/ asd9*3+1 *3+ 5*dd4");
	}
	
	@Test
	public void resolutorResuelveCorrectamenteLaCuenta(){
        Assert.assertEquals(ResolutorDeCuentas.resolver("27/9*3+ 1*3+5  *4"), 32, 0);
	}
	
	@Test
	public void empresaDeFormatoArchivo(){
        String empresa = "{\"nombre\":\"Coca-cola2\", \"cuentas\":[{\"nombre\":\"juanca\",\"anio\":\"2000\",\"valor\":\"2000\"}]}";
        Empresa resultado =  conversor.deFormatoArchivo(empresa, Empresa.class);
        Assert.assertEquals(resultado.getNombre(), "Coca-cola2");
	}
	
	
	@Test
	public void empresaAFormatoArchivo(){
		Empresa unaEmpresa = new Empresa("Hello Bussiness world");
		unaEmpresa.setCuentas(new ArrayList<Cuenta>());
		unaEmpresa.agregarCuenta(new Cuenta("pepe", "111", "222"));
		String json = conversor.aFormatoArchivo(unaEmpresa);
        Assert.assertEquals(json, "{\"nombre\":\"Hello Bussiness world\",\"cuentas\":[{\"nombre\":\"pepe\",\"periodo\":\"111\",\"valor\":\"222\"}]}");
    }
	
   @Test
   public void deFormatoArchivoValido(){
        String empresa = "{nombre:\"Coca-cola\", \"cuentas\": [{\"nombre\": \"Juancito\", \"anio\": \"2017\", \"valor\":\"1525\"}]}";
        Empresa resultado =  conversor.deFormatoArchivo(empresa, Empresa.class);
        Assert.assertEquals(resultado.getNombre(), "Coca-cola");
    }

    @Test
    public void manejadorAgregaEmpresaAlArchivoCorrectamente() {
        Empresa nuevaEmpresa = new Empresa("popo");
        manejador.agregarEmpresaAlArchivo(nuevaEmpresa);
        RepositorioEmpresas repositorioEmpresas = manejador.getRepositorioEmpresas();
        Assert.assertEquals(repositorioEmpresas.getLista().get(0).getNombre(), "popo");
    }
    
   @Test(expected = CuentaPreexistenteException.class)
    public void viewModelAgregaCuentaRepetidaAlArchivo(){
    	agregarEmpresaViewModel.setEmpresa("Seven Up");
    	agregarEmpresaViewModel.agregarEmpresa();
    	Empresa empresa = manejador.getRepositorioEmpresas().getLista().get(0);
    	cuentasViewModel.setEmpresa(empresa);
    	cuentasViewModel.setNombre("Patrimonio Neto");
    	cuentasViewModel.setPeriodo("1000");
    	cuentasViewModel.setValor("2000");
    	cuentasViewModel.agregarCuenta();
    	cuentasViewModel.agregarCuenta();
    }

    @Test
    public void viewModelAgregarCuentaAlArchivoCorrectamente(){
    	agregarEmpresaViewModel.setEmpresa("Seven Up");
    	agregarEmpresaViewModel.agregarEmpresa();
    	Empresa empresa = manejador.getRepositorioEmpresas().getLista().get(0);
    	cuentasViewModel.setEmpresa(empresa);
    	cuentasViewModel.setNombre("Patrimonio Neto");
    	cuentasViewModel.setPeriodo("1000");
    	cuentasViewModel.setValor("2000");
    	cuentasViewModel.agregarCuenta();
    	Assert.assertEquals(manejador.getRepositorioEmpresas().getLista().get(0).getCuentas().get(0).getNombre(), "Patrimonio Neto");
    }
    
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConAnioInvalido(){
    	cuentasViewModel.setPeriodo("./zxc");
    }
    
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConNombreInvalido(){
    	cuentasViewModel.setNombre(null);
    }
    
    @Test(expected = CuentaInvalidaException.class)
    public void cuentaConValorInvalido(){
    	cuentasViewModel.setValor(null);
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExtension(){
    	Archivo.validarRutaArchivo("rutaInvalida.tar");
    }
    
    @Test(expected = ArchivoInvalidoException.class)
    public void archivoInvalidoPorExistencia(){
    	Archivo.validarRutaArchivo("rutaInvalida.txt");
    }
  
    @After
    public void after(){
        file.delete();
    }
    
}

















