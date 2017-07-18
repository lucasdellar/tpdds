import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import OperacionesMatematicas.ResolutorDeCuentas;
import comparadores.ComparadorMayor;
import comparadores.ComparadorMenor;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import criterios.CriterioCrecimiento;
import criterios.CriterioPorValor;
import criterios.Mediana;
import criterios.Promedio;
import criterios.Sumatoria;
import domain.Archivo;
import domain.ConversorFormatoArchivo;
import domain.Cuenta;
import domain.IConversorFormatoArchivo;
import domain.Indicador;
import domain.Metodologia;
import domain.Valor;
import domain.ValorIndicador;
import domain.DomainExceptions.ArchivoInvalidoException;
import domain.DomainExceptions.CuentaInvalidaException;
import domain.DomainExceptions.CuentaPreexistenteException;
import domain.DomainExceptions.ParserException;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import repositorios.RepositorioEmpresas;
import repositorios.RepositorioIndicadores;
import ui.ViewModels.AgregarEmpresaViewModel;
import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.InviertiendoViewModel;

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
		Valor valor = new ValorIndicador(unIndicador, taxativa.getRepoIndicadores());
		taxativa.setCriterio(new Promedio(valor));
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
		Valor valor = new ValorIndicador(unIndicador, taxativa.getRepoIndicadores());
		taxativa.setCriterio(new Promedio(valor));
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
		Valor valor = new ValorIndicador(unIndicador, prioritaria.getRepoIndicadores());
		prioritaria.setCriterio(new Sumatoria(valor));
		
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
		Assert.assertEquals(prioritaria.aplicar(empresas).get(1).getNombre(), "testEmpresa3");
		Assert.assertEquals(prioritaria.aplicar(empresas).get(2).getNombre(), "testEmpresa2");
	}
	
//	@Test
//	public void metodología_solo_prioritarias(){
//		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
//		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
//		 * en primer lugar.
//		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
//		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
//		 */
//		
//		RepositorioIndicadores repositorio = new RepositorioIndicadores();
//		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
//		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
//		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
//		CondicionPrioritaria prioritaria1 = new CondicionPrioritaria(repositorio, new ComparadorMayor(), 1);
//		CondicionPrioritaria prioritaria2 = new CondicionPrioritaria(repositorio, new ComparadorMenor(), 3);
//		CondicionPrioritaria prioritaria3 = new CondicionPrioritaria(repositorio, new ComparadorMayor(), 5);
//		
//		List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<>();
//		List<CondicionTaxativa> condicionesTaxativasVacia = new ArrayList<>();
//		
//		condicionesPrioritarias.add(prioritaria1);
//		condicionesPrioritarias.add(prioritaria2);
//		condicionesPrioritarias.add(prioritaria3);
//		Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativasVacia, condicionesPrioritarias);
//		
//		repositorio.agregar(unIndicador);
//		repositorio.agregar(otroIndicador);
//		repositorio.agregar(tercerIndicador);
//		
//		Valor valorUno = new ValorIndicador(unIndicador, prioritaria1.getRepoIndicadores());
//		Valor valorDos = new ValorIndicador(otroIndicador, prioritaria2.getRepoIndicadores());
//		Valor valorTres = new ValorIndicador(tercerIndicador, prioritaria3.getRepoIndicadores());
//		prioritaria1.setCriterio(new Sumatoria(valorUno));
//		prioritaria2.setCriterio(new Promedio(valorDos));
//		prioritaria3.setCriterio(new Promedio(valorTres));
//		
//		Empresa empresa1 = new Empresa("testEmpresa1");
//		empresa1.setCuentas(new ArrayList<>());
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 75
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
//		
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "15")); //Promedio: 15
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "15"));
//		
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //Promedio: 9.15
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
//		
//		Empresa empresa2 = new Empresa("testEmpresa2");
//		empresa2.setCuentas(new ArrayList<>());
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 30
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
//		
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "5"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "6")); //Promedio: 5
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "7"));
//		
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));
//		
//		Empresa empresa3 = new Empresa("testEmpresa3");
//		empresa3.setCuentas(new ArrayList<>());
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 150
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
//		
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
//		
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
//		
//		/* prioritaria1, peso 1 = empresa3, empresa2, empresa1 => Rankings E1 E2 E3: 1*(3-2), 1*(3-1), 1*(3-0).
//		 * prioritaria2, peso 3 = empresa2, empresa3, empresa1 => Rankings E1 E2 E3: 3*(3-2), 3*(3-0) ,3*(3-1).
//		 * prioritaria3, peso 5 = empresa3, empresa1, empresa2 => Rankings E1 E2 E3: 5*(3-1), 5*(3-2), 5*(3-0).
//		 * Rankings E1, E2, E3 = 14, 16, 24 => empresa3, empresa2, empresa1.
//		 */
//		
//		List<Empresa> empresas = new ArrayList<>();
//		empresas.add(empresa1);
//		empresas.add(empresa2);
//		empresas.add(empresa3);
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(0).getNombre(), "testEmpresa3");
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(1).getNombre(), "testEmpresa2");
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(2).getNombre(), "testEmpresa1");
//	}
	
//	@Test
//	public void metodología_solo_taxativas(){
//		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
//		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
//		 * en primer lugar.
//		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
//		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
//		 */
//		
//		RepositorioIndicadores repositorio = new RepositorioIndicadores();
//		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
//		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
//		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
//		CondicionTaxativa taxativa1 = new CondicionTaxativa(repositorio, new ComparadorMayor(), 20);
//		CondicionTaxativa taxativa2 = new CondicionTaxativa(repositorio, new ComparadorMayor());
//		CondicionTaxativa taxativa3 = new CondicionTaxativa(repositorio, new ComparadorMayor());
//		List<CondicionTaxativa> condicionesTaxativas = new ArrayList<>();
//		List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<>();
//		condicionesTaxativas.add(taxativa1);
//		condicionesTaxativas.add(taxativa2);
//		condicionesTaxativas.add(taxativa3);
//		
//		Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativas, condicionesPrioritarias);
//		
//		repositorio.agregar(unIndicador);
//		Valor valorUno = new ValorIndicador(unIndicador, taxativa1.getRepoIndicadores());
//		Valor valorDos = new ValorIndicador(otroIndicador, taxativa2.getRepoIndicadores());
//		Valor valorTres = new ValorIndicador(tercerIndicador, taxativa3.getRepoIndicadores());
//		taxativa1.setCriterio(new Mediana(valorUno));
//		taxativa2.setCriterio(new CriterioCrecimiento(valorDos, 2015, 2017, 1));
//		taxativa3.setCriterio(new CriterioCrecimiento(valorTres, 2015, 2017, 0));
//		
//		Empresa empresa1 = new Empresa("testEmpresa1");
//		empresa1.setCuentas(new ArrayList<>());
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 15
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
//		
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "20")); //CrecimientoCasiSiempre: 15
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "25"));
//		
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //CrecimientoSiempre: 9.15
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
//		
//		Empresa empresa2 = new Empresa("testEmpresa2");
//		empresa2.setCuentas(new ArrayList<>());
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 6
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
//		
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "4"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "5")); //Promedio: 5
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "6"));
//		
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));
//		
//		Empresa empresa3 = new Empresa("testEmpresa3");
//		empresa3.setCuentas(new ArrayList<>());
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 30
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
//		
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
//		
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
//		
//		/* prioritaria1, peso 1 = empresa3, empresa2, empresa1 => Rankings E1 E2 E3: 1*(3-2), 1*(3-1), 1*(3-0).
//		 * prioritaria2, peso 3 = empresa2, empresa3, empresa1 => Rankings E1 E2 E3: 3*(3-2), 3*(3-0) ,3*(3-1).
//		 * prioritaria3, peso 5 = empresa3, empresa1, empresa2 => Rankings E1 E2 E3: 5*(3-1), 5*(3-2), 5*(3-0).
//		 * Rankings E1, E2, E3 = 14, 16, 24 => empresa3, empresa2, empresa1.
//		 */
//		
//		List<Empresa> empresas = new ArrayList<>();
//		empresas.add(empresa1);
//		empresas.add(empresa2);
//		empresas.add(empresa3);
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(0).getNombre(), "testEmpresa3");
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).size(), 1);
//	}
//	
//	@Test
//	public void metodología_taxativas_prioritarias(){
//		/*Objetivo: Ordenar la lista de empresas según el peso adherido por la
//		 * condición prioritaria adherida. La empresa testEmpresa1 debería quedar
//		 * en primer lugar.
//		 * Resultado: Una lista ordenada, con testEmpresa1 en primer lugar, 
//		 * testEmpresa3 en segundo lugar y testEmpresa2 en el tercer puesto.
//		 */
//		
//		RepositorioIndicadores repositorio = new RepositorioIndicadores();
//		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
//		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
//		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
//		CondicionTaxativa taxativa1 = new condicionesTaxativas(repositorio, new ComparadorMayor(), 1);
//		CondicionTaxativa taxativa2 = new condicionesTaxativas(repositorio, new ComparadorMenor(), 3);
//		CondicionTaxativa taxativa3 = new condicionesTaxativas(repositorio, new ComparadorMayor(), 5);
//		List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<>();
//		condicionesTaxativas.add(taxativa1);
//		condicionesTaxativas.add(taxativa2);
//		condicionesTaxativas.add(taxativa3);
//		Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativas);
//		
//		repositorio.agregar(unIndicador);
//		taxativa1.setCriterio(new Sumatoria(unIndicador));
//		taxativa2.setCriterio(new CrecimientoCasiSiempre(otroIndicador));
//		taxativa3.setCriterio(new CrecimientoSiempre(tercerIndicador));
//		
//		Empresa empresa1 = new EmpresaRankeada("testEmpresa1");
//		empresa1.setCuentas(new ArrayList<>());
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2015", "5"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2016", "5")); //Sumatoria: 15
//		empresa1.agregarCuenta(new Cuenta("testCuentaA", "2017", "5"));
//		
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2015", "15"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2016", "15")); //Promedio: 15
//		empresa1.agregarCuenta(new Cuenta("testCuentaB", "2017", "15"));
//		
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2015", "20"));
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2016", "20")); //Promedio: 9.15
//		empresa1.agregarCuenta(new Cuenta("testCuentaC", "2017", "15"));
//		
//		Empresa empresa2 = new EmpresaRankeada("testEmpresa2");
//		empresa2.setCuentas(new ArrayList<>());
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2014", "1"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2015", "2")); //Sumatoria: 6
//		empresa2.agregarCuenta(new Cuenta("testCuentaA", "2016", "3"));
//		
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2014", "4"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2015", "5")); //Promedio: 5
//		empresa2.agregarCuenta(new Cuenta("testCuentaB", "2016", "6"));
//		
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2014", "7"));
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2015", "8")); //Promedio: 4
//		empresa2.agregarCuenta(new Cuenta("testCuentaC", "2016", "9"));
//		
//		Empresa empresa3 = new EmpresaRankeada("testEmpresa3");
//		empresa3.setCuentas(new ArrayList<>());
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2013", "10"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2014", "10")); //Sumatoria: 30
//		empresa3.agregarCuenta(new Cuenta("testCuentaA", "2015", "10"));
//		
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2013", "10"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2014", "10")); //Promedio: 10
//		empresa3.agregarCuenta(new Cuenta("testCuentaB", "2015", "10"));
//		
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2013", "25"));
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2014", "25")); //Promedio: 12.5
//		empresa3.agregarCuenta(new Cuenta("testCuentaC", "2015", "25"));
//		
//		/* prioritaria1, peso 1 = empresa3, empresa2, empresa1 => Rankings E1 E2 E3: 1*(3-2), 1*(3-1), 1*(3-0).
//		 * prioritaria2, peso 3 = empresa2, empresa3, empresa1 => Rankings E1 E2 E3: 3*(3-2), 3*(3-0) ,3*(3-1).
//		 * prioritaria3, peso 5 = empresa3, empresa1, empresa2 => Rankings E1 E2 E3: 5*(3-1), 5*(3-2), 5*(3-0).
//		 * Rankings E1, E2, E3 = 14, 16, 24 => empresa3, empresa2, empresa1.
//		 */
//		
//		List<Empresa> empresas = new ArrayList<>();
//		empresas.add(empresa1);
//		empresas.add(empresa2);
//		empresas.add(empresa3);
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(0).getNombre(), "testEmpresa3");
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(1).getNombre(), "testEmpresa2");
//		Assert.assertEquals(metodologia.aplicarMetodologia(empresas).get(2).getNombre(), "testEmpresa1");
//	} NO FUNCIONAAAAAAAAAAAAAAAAAAAAAAAAAAAAA why god whyy
	
	@Test
	public void agregar_condicion_Taxativa(){
		CondicionTaxativa crece = new CondicionTaxativa(new RepositorioIndicadores(), new ComparadorMayor());
		List<CondicionTaxativa> condiciones_taxativas = new ArrayList<>();
		condiciones_taxativas.add(crece);
		Metodologia metodologia = new Metodologia("testMetodologia", condiciones_taxativas, null);
		Assert.assertEquals(metodologia.getCondiciones_taxativas().size(), 1);
	}
	
	@Test
	public void agregar_condicion_Prioritaria(){
		CondicionPrioritaria unaCondicion = new CondicionPrioritaria(new RepositorioIndicadores(), new ComparadorMayor(), 5);
		List<CondicionPrioritaria> condiciones_prioritarias = new ArrayList<>();
		List<CondicionTaxativa> condiciones_taxativas = new ArrayList<>();
		condiciones_prioritarias.add(unaCondicion);
		Metodologia metodologia = new Metodologia("testMetodologia", condiciones_taxativas,condiciones_prioritarias);
		Assert.assertEquals(metodologia.getCondiciones_prioritarias().size(), 1);
		Assert.assertEquals(metodologia.getCondiciones_taxativas().size(), 0);	
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
		
		List<CondicionTaxativa> condiciones_taxativas = new ArrayList<CondicionTaxativa>();
		List<CondicionPrioritaria> condiciones_prioritarias = new ArrayList<CondicionPrioritaria>();
		Metodologia metodologiaTest = new Metodologia("testMetodologia", condiciones_taxativas, condiciones_prioritarias);
		
		metodologiaTest.ordenarPorRanking(empresas);
		Assert.assertEquals(empresas.get(0).getNombre(), "testEmpresa3");
		Assert.assertEquals(empresas.get(1).getNombre(), "testEmpresa1");
		Assert.assertEquals(empresas.get(2).getNombre(), "testEmpresa2");
	}

	@Test
	public void CriterioCrecimiento_FormaEstricta_EvaluaCorrectamente(){
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		CondicionTaxativa condicion = new CondicionTaxativa(repositorio, new ComparadorMayor());
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		Valor unValor = new ValorIndicador(unIndicador, condicion.getRepoIndicadores());
		condicion.setCriterio(new CriterioCrecimiento(unValor, 2015, 2017, 0));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada unaEmpresa = new EmpresaRankeada("testEmpresa1");
		unaEmpresa.setCuentas(new ArrayList<>());
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));
		empresas.add(unaEmpresa);
		
		EmpresaRankeada otraEmpresa = new EmpresaRankeada("testEmpresa2");
		otraEmpresa.setCuentas(new ArrayList<>());
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "5"));
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "4"));
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "6"));
		empresas.add(otraEmpresa);
		
		Assert.assertEquals(condicion.aplicar(empresas).size(), 1);
		Assert.assertEquals(condicion.aplicar(empresas).get(0).getNombre(), "testEmpresa1");
	}
	
	@Test
	public void CriterioCrecimiento_FormaPermisiva_EvaluaCorrectamente(){
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		CondicionTaxativa condicion = new CondicionTaxativa(repositorio, new ComparadorMayor());
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		Valor unValor = new ValorIndicador(unIndicador, condicion.getRepoIndicadores());
		condicion.setCriterio(new CriterioCrecimiento(unValor, 2015, 2017, 1));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		
		EmpresaRankeada unaEmpresa = new EmpresaRankeada("testEmpresa1");
		unaEmpresa.setCuentas(new ArrayList<>());
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "5"));
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "4"));
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "6"));
		empresas.add(unaEmpresa);
		
		EmpresaRankeada otraEmpresa = new EmpresaRankeada("testEmpresa2");
		otraEmpresa.setCuentas(new ArrayList<>());
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "3"));
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "4"));
		otraEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "5"));
		empresas.add(otraEmpresa);
		
		Assert.assertEquals(condicion.aplicar(empresas).size(), 2);
	}
	
	@Test
	public void cumpleCondicionTaxativaConCriterioMediana(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		CondicionTaxativa condicion = new CondicionTaxativa(repo, new ComparadorMayor(), 2);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		Valor valor = new ValorIndicador(unIndicador, condicion.getRepoIndicadores());
		condicion.setCriterio(new Mediana(valor));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));
		empresas.add(miEmpresa);		
		Assert.assertEquals(condicion.aplicar(empresas).size(), 1);
	}
	
	@Test 
	public void cumpleCondicionTaxativaConCriterioPorValor(){
		RepositorioIndicadores repo = new RepositorioIndicadores();
		CondicionTaxativa condicion = new CondicionTaxativa(repo, new ComparadorMayor(), 2);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta + 1");
		Valor valor = new ValorIndicador(unIndicador, condicion.getRepoIndicadores());
		valor.setPeriodo("2015");
		condicion.setCriterio(new CriterioPorValor(valor));
		ArrayList<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada miEmpresa = new EmpresaRankeada("testEmpresa");
		miEmpresa.setCuentas(new ArrayList<>());
		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
		empresas.add(miEmpresa);		
		Assert.assertEquals(condicion.aplicar(empresas).size(), 1);
	}
	
	@Test
	public void cumpleCondicionPrioritariaConCriterioSumatoria(){
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		CondicionPrioritaria condicion = new CondicionPrioritaria(repositorio, new ComparadorMayor(), 5);
		Indicador unIndicador = new Indicador("indicadorTest", "testCuenta * 2");
		Valor unValor = new ValorIndicador(unIndicador, condicion.getRepoIndicadores());
		condicion.setCriterio(new Sumatoria(unValor));
		List<EmpresaRankeada> empresas = new ArrayList<EmpresaRankeada>();
		EmpresaRankeada unaEmpresa = new EmpresaRankeada("testEmpresa");
		
		unaEmpresa.setCuentas(new ArrayList<>());
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "5"));
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "15"));
		unaEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "5"));
		empresas.add(unaEmpresa);
		
		Assert.assertEquals(condicion.aplicar(empresas).get(0).getNombre(), "testEmpresa");
		Assert.assertEquals(condicion.getCriterio().calcular(unaEmpresa), 50, 0);
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

    /*@Test
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
    }*/
    
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

















