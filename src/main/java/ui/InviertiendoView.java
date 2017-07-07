package ui;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;

import domain.Archivo;
import domain.Cuenta;
import empresas.Empresa;
import manejadoresArchivo.ManejadorDeArchivoIndicadores;

import org.uqbar.arena.windows.MessageBox;
import org.uqbar.ui.view.ErrorViewer;

import ui.Dialogs.AgregarEmpresaDialog;
import ui.Dialogs.AgregarIndicadorDialog;
import ui.Dialogs.AgregarMetodologiaDialog;
import ui.Dialogs.ArchivoDialog;
import ui.Dialogs.CrearCuentaDialog;
import ui.Dialogs.EmpresaDialog;
import ui.Dialogs.EvaluarEmpresaDialog;
import ui.Dialogs.EvaluarMetolodigaDialog;
import ui.ViewModels.AgregarCondicionViewModel;
import ui.ViewModels.AgregarEmpresaViewModel;
import ui.ViewModels.AgregarIndicadorViewModel;
import ui.ViewModels.AgregarMetodologiaViewModel;
import ui.ViewModels.ArchivoViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.EvaluarEmpresaViewModel;
import ui.ViewModels.EvaluarMetodologiaViewModel;
import ui.ViewModels.InviertiendoViewModel;

public class InviertiendoView extends MainWindow<InviertiendoViewModel> implements ErrorViewer{

	public InviertiendoView() {
		super(new InviertiendoViewModel());
		archivoIndicadores.setRuta("indicadores.txt");
		archivoMetodologias.setRuta("metodologias.txt");
	}
	private Archivo archivoEmpresas = new Archivo();
	private Archivo archivoIndicadores = new Archivo();
	private Archivo archivoMetodologias = new Archivo();
	private EmpresaViewModel empresaViewModel;

	protected void openArchivoDialog() {
		ArchivoDialog archivoDialog = new ArchivoDialog(this, new ArchivoViewModel());
		archivoDialog.onAccept(() -> {
			archivoEmpresas.setRuta(archivoDialog.getRutaArchivo());
			this.getModelObject().setRutaArchivo(archivoDialog.getRutaArchivo());
			//this.getModelObject().mostrarCuentas(); 
		});
		archivoDialog.open();
	}

	protected void openCrearCuentaDialog() {
		CrearCuentaDialog crearCuentaDialog = new CrearCuentaDialog(this,archivoEmpresas);
		crearCuentaDialog.getModelObject().setEmpresa(this.getModelObject().getEmpresa());
		crearCuentaDialog.open();
		//this.getModelObject().actualizarEmpresas();
	}

	protected void openEmpresaDialog() {
		empresaViewModel = new EmpresaViewModel(archivoEmpresas);
		EmpresaDialog empresaDialog = new EmpresaDialog(this, empresaViewModel);
		empresaDialog.open();
	}
	
	
	@Override
	public void createContents(Panel mainPanel) {
		
		openArchivoDialog();
		this.setTitle("Invirtiendo");
		this.getDelegate().setErrorViewer(this);

		mainPanel.setLayout(new VerticalLayout());
		
		
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


    private void openEvaluarEmpresaDialog() {
    	EvaluarEmpresaViewModel evaluarViewModel = new EvaluarEmpresaViewModel(this.getModelObject().getEmpresa(), new ManejadorDeArchivoIndicadores(archivoIndicadores.getRuta()).getRepositorioIndicadores());
    	EvaluarEmpresaDialog evaluarIndicadorDialog = new EvaluarEmpresaDialog(this, evaluarViewModel);
    	evaluarIndicadorDialog.open();
    }

	private void openCrearIndicadorDialog() {
    	AgregarIndicadorViewModel agregarViewModel = new AgregarIndicadorViewModel(archivoIndicadores.getRuta());
    	AgregarIndicadorDialog agregarIndicadorDialog = new AgregarIndicadorDialog(this, agregarViewModel);
    	agregarIndicadorDialog.open();
	}
	

	private void openAgregarMetodologiaDialog() {
		AgregarMetodologiaViewModel agregarViewModel = new AgregarMetodologiaViewModel(archivoMetodologias.getRuta());
		agregarViewModel.setCondiciones(new ArrayList<>());
		AgregarMetodologiaDialog agregarMetodologiaDialog = new AgregarMetodologiaDialog(this, agregarViewModel, new ManejadorDeArchivoIndicadores(archivoIndicadores.getRuta()).getRepositorioIndicadores());
		agregarMetodologiaDialog.open();
	}
	
	private void openEvaluarMetodologiaDialog() {
    	EvaluarMetodologiaViewModel evaluarViewModel = new EvaluarMetodologiaViewModel();
    	EvaluarMetolodigaDialog evaluarMetodologiaDialog = new EvaluarMetolodigaDialog(this, evaluarViewModel);
    	evaluarMetodologiaDialog.open();
    }
	
	private void openCrearEmpresaDialog() {
    	AgregarEmpresaViewModel agregarViewModel = new AgregarEmpresaViewModel(archivoEmpresas);
 		AgregarEmpresaDialog agregarEmpresaDialog = new AgregarEmpresaDialog(this, agregarViewModel);
 		agregarEmpresaDialog.onAccept(() -> {agregarViewModel.agregarEmpresa(); this.getModelObject().actualizarEmpresas();});
 		agregarEmpresaDialog.open();
 	}

	private void agregarTextBox(Panel nuevaCuentaPanel, String property) {
		new TextBox(nuevaCuentaPanel).setWidth(150).bindValueToProperty(property);
	}

	private void agregarNumerico(Panel nuevaCuentaPanel, String property) {
		new NumericField(nuevaCuentaPanel).setWidth(150).bindValueToProperty(property);
	}

	private void agregarLabel(Panel nuevaCuentaPanel, String texto) {
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
		new InviertiendoView().startApplication();
	}

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
