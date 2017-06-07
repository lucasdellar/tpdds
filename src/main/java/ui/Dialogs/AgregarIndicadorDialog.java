package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import ui.ViewModels.AgregarEmpresaViewModel;
import ui.ViewModels.AgregarIndicadorViewModel;

public class AgregarIndicadorDialog extends  Dialog<AgregarIndicadorViewModel> {
	
	public AgregarIndicadorDialog(WindowOwner owner, AgregarIndicadorViewModel model) {
		super(owner, model);
		this.setTitle("Agregar Indicador");
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Ingrese el nombre del indicador a agregar");
        new TextBox(form).bindValueToProperty("nombre");        
        
        new Label(form).setText("Ingrese la formula del indicador");
        new TextBox(form).bindValueToProperty("formula");    
        
        this.onAccept(() -> this.getModelObject().agregarIndicador());
        
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
