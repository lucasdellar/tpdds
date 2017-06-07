package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.Action;

import domain.Archivo;
import domain.Empresa;
import domain.RepositorioEmpresas;
import ui.InviertiendoView;
import ui.ViewModels.AgregarEmpresaViewModel;
import ui.ViewModels.CuentaViewModel;
import ui.ViewModels.EmpresaViewModel;
import ui.ViewModels.InviertiendoViewModel;


public class EmpresaDialog extends Dialog<EmpresaViewModel> {

	private Archivo archivo;
	
    public EmpresaDialog(WindowOwner owner, EmpresaViewModel model) {
		super(owner, model);
		this.setTitle("Seleccione una Empresa");
	}

    @Override
    protected void createFormPanel(Panel mainPanel) {
        Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Selecionar Empresa");
        
		Selector<Empresa> selector = new Selector<Empresa>(form);
		selector.allowNull(false);
		selector.bindValueToProperty("empresa.nombre");
		selector.bindItemsToProperty("empresas.empresas");
		
        
        InviertiendoView.crearBoton(mainPanel, "Agregar Empresa").onClick(() -> openCrearEmpresaDialog());
        
        this.onAccept(() ->
        {
            ;
        });
    }

    private void openCrearEmpresaDialog() {
    	AgregarEmpresaViewModel agregarViewModel = new AgregarEmpresaViewModel(this.getModelObject().getArchivo());
 		AgregarEmpresaDialog agregarEmpresaDialog = new AgregarEmpresaDialog(this, agregarViewModel);
 		agregarEmpresaDialog.onAccept(() -> {agregarViewModel.agregarEmpresa(); this.getModelObject().actualizarEmpresas();});
 		agregarEmpresaDialog.open();
 	}

	@Override
    protected void addActions(Panel actions) {
        new Button(actions)
                .setCaption("Administrar Empresa")
                .onClick(this::accept)
                .setAsDefault()
                .disableOnError();

        new Button(actions) //
                .setCaption("Cancelar")
                .onClick(this::cancel);
    }
	
	
	
	
	
}
	
	