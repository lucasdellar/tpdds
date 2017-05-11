package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import domain.Archivo;
import ui.ViewModels.ArchivoViewModel;


public class ArchivoDialog extends Dialog<ArchivoViewModel> {

	
    public String getRutaArchivo(){
        return this.getModelObject().getRuta(); 
    }
    
    public String getArchivo(){
    	return this.getModelObject().getRuta();
    }

    public ArchivoDialog(WindowOwner owner, ArchivoViewModel model) {
        super(owner, model);
        this.setTitle("Ingrese un archivo fuente");
    }

    @Override
    protected void createFormPanel(Panel mainPanel) {
        Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));

        new Label(form).setText("Ingrese ruta del archivo:");
        new TextBox(mainPanel).setWidth(150).bindValueToProperty("ruta");
    }

    @Override
    protected void addActions(Panel actions) {
        new Button(actions)
                .setCaption("Aceptar")
                .onClick(this::accept)
                .setAsDefault()
                .disableOnError();
    }
}
