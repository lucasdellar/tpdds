package ui;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;

import domain.Cuenta;
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.ui.view.ErrorViewer;
import ui.Dialogs.ArchivoDialog;
import ui.Dialogs.CrearCuentaDialog;
import ui.ViewModels.InviertiendoViewModel;

public class InviertiendoView extends MainWindow<InviertiendoViewModel> implements ErrorViewer{

	public InviertiendoView() {
		super(new InviertiendoViewModel());
	}
	private Archivo archivo;


	protected void openArchivoDialog() {
		archivo = new Archivo();
		ArchivoDialog archivoDialog = new ArchivoDialog(this, archivo);
		archivoDialog.onAccept(() -> {
			InviertiendoViewModel viewModel = this.getModelObject();
			viewModel.setRutaArchivo(archivo.getRuta());
			viewModel.mostrarCuentas();
		});
		archivoDialog.open();
	}

	protected void openCrearCuentaDialog() {
		CrearCuentaDialog crearCuentaDialog = new CrearCuentaDialog(this,archivo);
		crearCuentaDialog.open();
		this.getModelObject().mostrarCuentas();
	}

	@Override
	public void createContents(Panel mainPanel) {
		
		this.setTitle("Invirtiendo");
		this.getDelegate().setErrorViewer(this);

		mainPanel.setLayout(new VerticalLayout());
		
		Panel filePanel = new Panel(mainPanel).setLayout(new ColumnLayout(2));
		
		agregarLabel(filePanel, "Archivo utilizado: ");
		
		agregarLabel(filePanel, this.getModelObject().getRutaArchivo());

		Panel tablePanel = new Panel(mainPanel).setLayout(new HorizontalLayout());

		Table<Cuenta> table = new Table<Cuenta>(tablePanel, Cuenta.class);
		table.bindItemsToProperty("repositorioCuentas.cuentas");
		table.setNumberVisibleRows(6);
		table.setWidth(1000);

		agregarColumna(table, "Cuenta", "nombre");
		agregarColumna(table, "Anio", "anio");
		agregarColumna(table, "Patrimonio Neto", "patrimonio_neto");

		Panel nuevaCuentaPanel = new Panel(mainPanel).setLayout(new ColumnLayout(3));

		crearBoton(mainPanel, "Agregar Cuenta").onClick(() -> openCrearCuentaDialog());

		openArchivoDialog();
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


	private void agregarColumna(Table<Cuenta> table, String titulo, String propiedad) {
		new Column<Cuenta>(table)
		.setTitle(titulo)
		.setFixedSize(100)
		.bindContentsToProperty(propiedad);
	}
	

	private Button crearBoton(Panel mainPanel, String textoBoton) {
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
