package ui;

import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

/**
 * Created by Matias Fischer on 07/05/2017.
 */

public class ArchivoDialog extends Dialog<Archivo> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ArchivoDialog(WindowOwner owner, Archivo model) {
        super(owner, model);
        this.setTitle("Ingrese un archivo fuente");
    }

    @Override
    protected void createFormPanel(Panel mainPanel) {
        Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));

        new Label(form).setText("Recibe resumen cuenta en domicilio");
        new CheckBox(form).bindValueToProperty("recibeResumenCuenta");

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
