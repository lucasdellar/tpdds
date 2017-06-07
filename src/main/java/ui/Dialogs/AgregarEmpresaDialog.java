package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import ui.ViewModels.AgregarEmpresaViewModel;


public class AgregarEmpresaDialog  extends Dialog<AgregarEmpresaViewModel> {

	public AgregarEmpresaDialog(WindowOwner owner, AgregarEmpresaViewModel model) {
		super(owner, model);
		this.setTitle("Agregar Empresa");
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
	  	Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Ingrese el nombre de la empresa a agregar");
        new TextBox(form).bindValueToProperty("empresa");        
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
