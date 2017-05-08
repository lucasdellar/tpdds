package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import ui.IArchivo;
import ui.ViewModels.CuentaViewModel;

/**
 * Created by Matias Fischer on 07/05/2017.
 */
public class CrearCuentaDialog extends Dialog<CuentaViewModel> {

    /**
     *
     */

    public CrearCuentaDialog(WindowOwner owner, IArchivo archivo) {
        super(owner, new CuentaViewModel(archivo));
        this.setTitle("Crear cuenta");
    }

    @Override
    protected void createFormPanel(Panel mainPanel) {
        Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));

        new Label(form).setText("Nombre");
        new TextBox(form).bindValueToProperty("nombre");

        new Label(form).setText("Año");
        new TextBox(form).bindValueToProperty("anio");

        new Label(form).setText("Patrimonio neto");
        new TextBox(form).bindValueToProperty("patrimonio_neto");

        this.onAccept(() ->
        {
            this.getModelObject().agregarCuenta();
        });
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