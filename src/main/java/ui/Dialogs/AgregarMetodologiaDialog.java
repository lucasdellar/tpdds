package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
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
		// TODO Auto-generated constructor stub
	}

	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Ingrese el nombre del indicador a agregar");
        new TextBox(form).bindValueToProperty("nombre");        
        
        new Label(form).setText("Ingrese la formula del indicador");
        new TextBox(form).bindValueToProperty("formula");    
        
        this.onAccept(() -> this.getModelObject().agregarMetodologia());
		
	}

	
	public void openAgregarCondicionDialog(){
		
		AgregarCondicionViewModel agregarViewModel = new AgregarMetodologiaViewModel();
		AgregarCondicionDialog agregarMetodologiaDialog = new AgregarMetodologiaDialog(this, agregarViewModel);
		AgregarCondicionDialog.open();
		
	}
}
