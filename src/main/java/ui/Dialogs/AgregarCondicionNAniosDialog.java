package ui.Dialogs;

import java.util.List;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import condiciones.CondicionTaxativa;
import ui.ViewModels.AgregarCondicionCrecimientoViewModel;
import ui.ViewModels.AgregarCondicionNAniosViewModel;

public class AgregarCondicionNAniosDialog extends  Dialog<AgregarCondicionNAniosViewModel>{

	List<CondicionTaxativa> condicionesYaAgregadas;
	
	public AgregarCondicionNAniosDialog(WindowOwner owner, AgregarCondicionNAniosViewModel model, List<CondicionTaxativa> condicionesYaAgregadas) {
		super(owner, model);
		this.condicionesYaAgregadas = condicionesYaAgregadas;
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {

		Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Ingrese el indicador deseado ");
        new TextBox(form).setWidth(150).bindValueToProperty("nombreIndicador");
		
        new Label(form).setText("Ingrese la cantidad de anios ");
        new TextBox(form).setWidth(150).bindValueToProperty("anios");
		
        new Label(form).setText("Escriba MAYOR o MENOR como criterio para comparar");
        new TextBox(form).setWidth(150).bindValueToProperty("mayorMenor");
		
        this.onAccept(() -> this.getModelObject().agregarCondicion(condicionesYaAgregadas));
        
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
