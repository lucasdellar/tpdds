package ui;

import java.io.IOException;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;

import domain.Cuenta;

public class InviertiendoView extends MainWindow<InviertiendoViewModel>{

	public InviertiendoView() {
		super(new InviertiendoViewModel());
	}

	
	@Override
	public void createContents(Panel mainPanel) {
		
		this.setTitle("Invirtiendo");
		
		mainPanel.setLayout(new VerticalLayout());
		
		crearBoton(mainPanel, "Mostrar Cuentas").onClick(() -> {
			try {this.getModelObject().mostrarCuentas();
			} catch (IOException e) {e.printStackTrace();}}); 
		
		Panel tablePanel = new Panel(mainPanel).setLayout(new HorizontalLayout());
		
		Table<Cuenta> table = new Table<Cuenta>(tablePanel, Cuenta.class);
		table.bindItemsToProperty("cuentas.cuentas");
		table.setNumberVisibleRows(6);
		table.setWidth(1000);

		agregarColumna(table, "Cuenta", "nombre");
		agregarColumna(table, "Periodo", "anio");
		agregarColumna(table, "Patrimonio Neto", "patrimonio_neto");
		
		Panel nuevaCuentaPanel = new Panel(mainPanel).setLayout(new ColumnLayout(3));
		
		agregarLabel(nuevaCuentaPanel, "Cuenta");
		agregarLabel(nuevaCuentaPanel, "Anio");
		agregarLabel(nuevaCuentaPanel, "Patrimonio Neto");
		agregarTextBox(nuevaCuentaPanel, "nuevaCuenta.nombre");
		agregarTextBox(nuevaCuentaPanel, "nuevaCuenta.anio");
		agregarTextBox(nuevaCuentaPanel, "nuevaCuenta.patrimonio_neto");
		
		crearBoton(mainPanel, "Agregar Cuenta").onClick(() -> {
			try { this.getModelObject().agregarCuenta();
			} catch (IOException e) { e.printStackTrace();}	});
			
	}


	private void agregarTextBox(Panel nuevaCuentaPanel, String property) {
		new TextBox(nuevaCuentaPanel).setWidth(150).bindValueToProperty(property);
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

}
