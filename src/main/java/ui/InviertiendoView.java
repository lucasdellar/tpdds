package ui;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.NumericField;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.ui.view.ErrorViewer;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import comparadores.Comparador;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import criterios.Promedio;
import criterios.Sumatoria;
import domain.Archivo;
import domain.Cuenta;
import domain.Indicador;
import domain.Metodologia;
import domain.Valor;
import domain.ValorIndicador;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import manejadoresArchivo.ManejadorDeArchivoEmpresas;
import manejadoresArchivo.ManejadorDeArchivoIndicadores;
import repositorios.RepositorioIndicadores;
import repositorios.RepositorioMetodologias;
import ui.Dialogs.AgregarEmpresaDialog;
import ui.Dialogs.AgregarIndicadorDialog;
import ui.Dialogs.AgregarMetodologiaDialog;
import ui.Dialogs.ArchivoDialog;
import ui.Dialogs.CrearCuentaDialog;
import ui.Dialogs.EmpresaDialog;
import ui.Dialogs.EvaluarEmpresaDialog;
import ui.Dialogs.EvaluarMetolodigaDialog;
import ui.ViewModels.AgregarEmpresaViewModel;
import ui.ViewModels.AgregarIndicadorViewModel;
import ui.ViewModels.AgregarMetodologiaViewModel;
import ui.ViewModels.ArchivoViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.EvaluarEmpresaViewModel;
import ui.ViewModels.EvaluarMetodologiaViewModel;
import ui.ViewModels.InviertiendoViewModel;

public class InviertiendoView extends MainWindow<InviertiendoViewModel> implements ErrorViewer{

	private Archivo archivoEmpresas = new Archivo();
	private RepositorioMetodologias repositorioMetodologias = new RepositorioMetodologias();
	private RepositorioIndicadores repositorioIndicadores = new RepositorioIndicadores();
	private EmpresaViewModel empresaViewModel;
	
	public InviertiendoView() {
		super(new InviertiendoViewModel());
		repositorioMetodologias.setLista(new ArrayList<Metodologia>());
		repositorioIndicadores.setLista(new ArrayList<Indicador>());
	}
	
	@Override
	public void createContents(Panel mainPanel) {

		openArchivoDialog();
		this.setTitle("Invirtiendo");

		this.getDelegate().setErrorViewer(this);

		mainPanel.setLayout(new VerticalLayout());

		agregarLabel(mainPanel, "Empresas");
	
		Panel tablePanel = new Panel(mainPanel).setLayout(new HorizontalLayout());

		Table<Empresa> table = new Table<Empresa>(tablePanel, Empresa.class);
		table.bindItemsToProperty("repositorioEmpresas.lista");
		table.setNumberVisibleRows(6);
		table.setWidth(3000);
		table.bindSelectionToProperty("empresa");

		agregarColumna(table, "Nombre", "nombre");

		crearBoton(mainPanel, "Agregar Empresa").onClick(() -> { openCrearEmpresaDialog();
		this.getModelObject().actualizarEmpresas(); });
		
		
		Panel filePanel = new Panel(mainPanel).setLayout(new ColumnLayout(2));
		
		agregarLabel(filePanel, "Archivo utilizado: " + this.getModelObject().getRutaArchivo());
		
		Panel cuentasPanel = new Panel(mainPanel).setLayout(new HorizontalLayout());

		Table<Cuenta> tablaCuentas = new Table<Cuenta>(cuentasPanel, Cuenta.class);
		tablaCuentas.bindItemsToProperty("empresa.cuentas");
		tablaCuentas.setNumberVisibleRows(6);
		tablaCuentas.setWidth(1000);
		
			
		agregarColumna(tablaCuentas, "Cuenta", "nombre");
		agregarColumna(tablaCuentas, "Periodo", "periodo");
		agregarColumna(tablaCuentas, "Valor", "valor");


		Panel nuevaCuentaPanel = new Panel(mainPanel).setLayout(new ColumnLayout(3));

		crearBoton(mainPanel, "Agregar Cuenta").onClick(() -> openCrearCuentaDialog());
		this.getModelObject().actualizarEmpresas();
		
		crearBoton(mainPanel, "Agregar Indicador").onClick(() -> openCrearIndicadorDialog());
		this.getModelObject().actualizarEmpresas();
		
		crearBoton(mainPanel, "Evaluar Empresa con Indicador").onClick(() -> openEvaluarEmpresaDialog());
		this.getModelObject().actualizarEmpresas();
		
		crearBoton(mainPanel, "Agregar Metodologia").onClick(() -> openAgregarMetodologiaDialog());
		
		crearBoton(mainPanel, "Evaluar empresa con Metodologia").onClick(() -> openEvaluarMetodologiaDialog());
	}
	
	/*********************************DIALOGS*********************************/

	protected void openArchivoDialog() {
		ArchivoDialog archivoDialog = new ArchivoDialog(this, new ArchivoViewModel());
		archivoDialog.onAccept(() -> {
			archivoEmpresas.setRuta(archivoDialog.getRutaArchivo());
			this.getModelObject().setRutaArchivo(archivoDialog.getRutaArchivo());
		});
		archivoDialog.open();
	}
	
	protected void openCrearCuentaDialog() {
		CrearCuentaDialog crearCuentaDialog = new CrearCuentaDialog(this,archivoEmpresas);
		crearCuentaDialog.getModelObject().setEmpresa(this.getModelObject().getEmpresa());
		crearCuentaDialog.open();
	}
	
	protected void openEmpresaDialog() {
		empresaViewModel = new EmpresaViewModel(archivoEmpresas);
		EmpresaDialog empresaDialog = new EmpresaDialog(this, empresaViewModel);
		empresaDialog.open();
	}

	private void openCrearEmpresaDialog() {
		AgregarEmpresaViewModel agregarViewModel = new AgregarEmpresaViewModel(archivoEmpresas);
		AgregarEmpresaDialog agregarEmpresaDialog = new AgregarEmpresaDialog(this, agregarViewModel);
		agregarEmpresaDialog.onAccept(() -> {agregarViewModel.agregarEmpresa(); this.getModelObject().actualizarEmpresas();});
		agregarEmpresaDialog.open();
	}
	
    private void openEvaluarEmpresaDialog() {
    	EvaluarEmpresaViewModel evaluarViewModel = new EvaluarEmpresaViewModel(this.getModelObject().getEmpresa(), repositorioIndicadores);
    	EvaluarEmpresaDialog evaluarIndicadorDialog = new EvaluarEmpresaDialog(this, evaluarViewModel);
    	evaluarIndicadorDialog.open();
    }

	private void openCrearIndicadorDialog() {
    	AgregarIndicadorViewModel agregarViewModel = new AgregarIndicadorViewModel(repositorioIndicadores);
    	AgregarIndicadorDialog agregarIndicadorDialog = new AgregarIndicadorDialog(this, agregarViewModel);
    	agregarIndicadorDialog.open();
	}
	

	private void openAgregarMetodologiaDialog() {
		AgregarMetodologiaViewModel agregarViewModel = new AgregarMetodologiaViewModel(repositorioMetodologias);
		agregarViewModel.setCondicionesPrioritarias(new ArrayList<CondicionPrioritaria>());
		agregarViewModel.setCondicionesTaxativas(new ArrayList<CondicionTaxativa>());
		AgregarMetodologiaDialog agregarMetodologiaDialog = new AgregarMetodologiaDialog(this, agregarViewModel, repositorioIndicadores);
		agregarMetodologiaDialog.open();
	} 
	
	private void openEvaluarMetodologiaDialog() {
    	EvaluarMetodologiaViewModel evaluarViewModel = new EvaluarMetodologiaViewModel(new ManejadorDeArchivoEmpresas(archivoEmpresas.getRuta()).getRepositorioEmpresas(), repositorioMetodologias);
    	EvaluarMetolodigaDialog evaluarMetodologiaDialog = new EvaluarMetolodigaDialog(this, evaluarViewModel);
    	evaluarMetodologiaDialog.open();
    }
	
	/*********************************VIEW ELEMENTS*********************************/
	
	private void agregarTextBox(Panel nuevaCuentaPanel, String property) {
		new TextBox(nuevaCuentaPanel).setWidth(150).bindValueToProperty(property);
	}

	private void agregarNumerico(Panel nuevaCuentaPanel, String property) {
		new NumericField(nuevaCuentaPanel).setWidth(150).bindValueToProperty(property);
	}

	public static void agregarLabel(Panel nuevaCuentaPanel, String texto) {
		new Label(nuevaCuentaPanel).setText(texto);
	}

	public static <T>void agregarColumna(Table<T> table, String titulo, String propiedad) {
		new Column<T>(table)
		.setTitle(titulo)
		.setFixedSize(100)
		.bindContentsToProperty(propiedad);
	}

	public static Button crearBoton(Panel mainPanel, String textoBoton) {
		Button boton =	new Button(mainPanel);
		boton.setCaption(textoBoton)
		.setHeight(25)
		.setWidth(217);
		return boton;
	}

	public static void main(String[] args) {
		
		/*EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		
	/*	-- 01: Persist an account.
		tx.begin();
		Cuenta unaCuenta = new Cuenta("Pepe", "202", "33");
		manager.persist(unaCuenta);
		tx.commit();
		
		-----------------------------------------------------
		
		 02: Persist a company.
		*/
//		tx.begin();
//		Empresa miEmpresa = new Empresa("testEmpresa");
//		miEmpresa.setCuentas(new ArrayList<>());
//		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2015", "2"));
//		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2016", "3"));
//		miEmpresa.agregarCuenta(new Cuenta("testCuenta", "2017", "4"));	
//		manager.persist(miEmpresa);
//		tx.commit();
		
//		 03: Find company.
//		
//		Empresa laEmpresa = manager.find(Empresa.class, 1l);
//		Assert.assertEquals(laEmpresa.getCuentas().size(), 3);
//		System.out.println(laEmpresa.getCuentas().size() == 3);
		
//		 04: Persist an indicator.
//		tx.begin();
//		Indicador unIndicador = new Indicador("ROE", "4 + 5 * Hola - 7");
//		manager.persist(unIndicador);
//		tx.commit();
//		
//		 05: Find an Indicator.
//		Indicador otroIndicador = manager.find(Indicador.class, 1l);
//		System.out.println(otroIndicador.getFormula());
//		
		/*
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		Indicador unIndicador = new Indicador("indicadorTestA", "testCuentaA * 5");
		Indicador otroIndicador = new Indicador("indicadorTestB", "testCuentaB + 25");
		Indicador tercerIndicador = new Indicador("indicadorTestC", "testCuentaC / 2");
		CondicionPrioritaria prioritaria1 = new CondicionPrioritaria(repositorio, Comparador.MAYOR, 1);
		CondicionPrioritaria prioritaria2 = new CondicionPrioritaria(repositorio, Comparador.MENOR, 3);
		CondicionPrioritaria prioritaria3 = new CondicionPrioritaria(repositorio, Comparador.MAYOR, 5);
		
		CondicionTaxativa taxativa = new CondicionTaxativa(repositorio, Comparador.MENOR, 100);	
		
		List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<>();
		List<CondicionTaxativa> condicionesTaxativas = new ArrayList<>();
		
		condicionesTaxativas.add(taxativa);
		
		
		condicionesPrioritarias.add(prioritaria1);
		condicionesPrioritarias.add(prioritaria2);
		condicionesPrioritarias.add(prioritaria3);
		
		repositorio.agregar(unIndicador);
		repositorio.agregar(otroIndicador);
		repositorio.agregar(tercerIndicador);
		
		Valor valor = new ValorIndicador(unIndicador.getNombre(), taxativa.getRepoIndicadores());
		Valor valorUno = new ValorIndicador(unIndicador.getNombre(), prioritaria1.getRepoIndicadores());
		Valor valorDos = new ValorIndicador(otroIndicador.getNombre(), prioritaria2.getRepoIndicadores());
		Valor valorTres = new ValorIndicador(tercerIndicador.getNombre(), prioritaria3.getRepoIndicadores());
		prioritaria1.setCriterio(new Sumatoria(valorUno));
		prioritaria2.setCriterio(new Promedio(valorDos));
		prioritaria3.setCriterio(new Promedio(valorTres));
		taxativa.setCriterio(new Promedio(valor));
		
    	tx.begin();
    	
    	Metodologia metodologia = new Metodologia("testMetodologia", condicionesTaxativas, condicionesPrioritarias);
    	manager.persist(metodologia);
    	
    	tx.commit();
    	
    	System.out.println(" termine " + manager.find(Metodologia.class, 1l).getNombre());
    	System.out.println(" taxativas " + manager.find(Metodologia.class, 1l).getCondiciones_taxativas().size());
    	System.out.println(" prioritarias " + manager.find(Metodologia.class, 1l).getCondiciones_prioritarias().size());
    	*/
//		RepositorioEmpresas repo = new RepositorioEmpresas();
//		
//		repo.traerEmpresas("empresas.txt");
		new InviertiendoView().startApplication();
	}
	
	/*********************************MESSAGES*********************************/

	@Override
	public void showInfo(String message) {
		showMessage(message, MessageBox.Type.Information);
	}

	@Override
	public void showWarning(String message) {
		showMessage(message, MessageBox.Type.Warning);
	}

	@Override
	public void showError(String message) {
		showMessage(message, MessageBox.Type.Error);
	}

	private void showMessage(String message, MessageBox.Type type){
		MessageBox messageBox = new MessageBox(this, type);
		messageBox.setMessage(message);
		messageBox.open();
	}

}
