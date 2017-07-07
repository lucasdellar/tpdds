package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import ui.ViewModels.AgregarCondicionViewModel;
import ui.ViewModels.AgregarIndicadorViewModel;
import ui.ViewModels.AgregarMetodologiaViewModel;

public class AgregarMetodologiaDialog extends  Dialog<AgregarMetodologiaViewModel> {

	public AgregarMetodologiaDialog(WindowOwner owner, AgregarMetodologiaViewModel model) {
		super(owner, model);
		this.setTitle("Agregar Metodología");
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Ingrese el nombre de la metodología a agregar");
        new TextBox(form).bindValueToProperty("nombre");        
        
        this.crearBoton(mainPanel, "Agregar Condiciones").onClick(() -> openAgregarCondicionDialog());   
        
        this.onAccept(() -> this.getModelObject().agregarMetodologia());
		
	}

	
	public void openAgregarCondicionDialog(){
		
		AgregarCondicionViewModel agregarViewModel = new AgregarCondicionViewModel();
		AgregarCondicionDialog agregarCondicionDialog = new AgregarCondicionDialog(this, agregarViewModel);
		agregarCondicionDialog.open();
	}
	
	public static Button crearBoton(Panel mainPanel, String textoBoton) {
		Button boton =	new Button(mainPanel);
		boton.setCaption(textoBoton)
		.setHeight(25)
		.setWidth(217);
		return boton;
	}
}
