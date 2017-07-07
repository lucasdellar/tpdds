package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import repositorios.RepositorioIndicadores;
import ui.InviertiendoView;
import ui.ViewModels.AgregarCondicionViewModel;
import ui.ViewModels.AgregarMetodologiaViewModel;

public class AgregarMetodologiaDialog extends  Dialog<AgregarMetodologiaViewModel> {

	RepositorioIndicadores repositorioIndicadores;
	
	public AgregarMetodologiaDialog(WindowOwner owner, AgregarMetodologiaViewModel model, RepositorioIndicadores repositorioIndicadores) {
		super(owner, model);
		this.setTitle("Agregar Metodologia");
		this.repositorioIndicadores = repositorioIndicadores;
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Ingrese el nombre de la metodologia a agregar");
        new TextBox(form).bindValueToProperty("nombre");        
        
        InviertiendoView.crearBoton(mainPanel, "Agregar Condiciones").onClick(() -> openAgregarCondicionDialog());   
        
        this.onAccept(() -> this.getModelObject().agregarMetodologia());
		
	}

	
	public void openAgregarCondicionDialog(){
		
		AgregarCondicionViewModel agregarViewModel = new AgregarCondicionViewModel();
		AgregarCondicionDialog agregarCondicionDialog = new AgregarCondicionDialog(this, agregarViewModel, 
				this.getModelObject().getCondiciones(), repositorioIndicadores);
		agregarCondicionDialog.open();
	}
	

	@Override
    protected void addActions(Panel actions) {
        new Button(actions)
                .setCaption("Aceptar")
                .onClick(this::accept)
                .setAsDefault()
                .disableOnError();

        new Button(actions) //
                .setCaption("Cancelar")
                .onClick(this::cancel);
    }
}
