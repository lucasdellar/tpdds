package ui.Dialogs;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import condiciones.Condicion;
import condiciones.CondicionPrioritaria;
import condiciones.CondicionTaxativa;
import repositorios.RepositorioIndicadores;
import ui.InviertiendoView;
import ui.ViewModels.AgregarCondicionCrecimientoViewModel;
import ui.ViewModels.AgregarCondicionViewModel;


public class AgregarCondicionDialog extends  Dialog<AgregarCondicionViewModel> {

	RepositorioIndicadores repositorioIndicadores;
	List<CondicionTaxativa> condicionesTaxativas;
	List<CondicionPrioritaria> condicionesPrioritarias;
	
	
	public AgregarCondicionDialog(WindowOwner owner, AgregarCondicionViewModel model, List<CondicionTaxativa> condicionesTaxativas,
			List<CondicionPrioritaria> condicionesPrioritarias, RepositorioIndicadores repositorioIndicadores) {
		super(owner, model);
		this.condicionesTaxativas = condicionesTaxativas;
		this.condicionesPrioritarias = condicionesPrioritarias;
		this.repositorioIndicadores = repositorioIndicadores;
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		
		new Label(form).setText("Seleccione el tipo de condicion deseada");
		
        form.setLayout(new ColumnLayout(2));
        
        InviertiendoView.crearBoton(mainPanel, "Mayor o menor a indicador en ultimos anios"
        		).onClick(() -> openAgregarCondicionNAniosDialog());   
        
        InviertiendoView.crearBoton(mainPanel, "Un indicador sea mayor o menor que el de otra empresa"
        		).onClick(() -> openAgregarCondicionCompararEmpresaDialog());   
        
        InviertiendoView.crearBoton(mainPanel, "Operacion matematica en base a indicador comparada con valor"
        		).onClick(() -> openAgregarCondicionOperacionMatematicaDialog());   
        
        InviertiendoView.crearBoton(mainPanel, "Crecimiento en un periodo"
        		).onClick(() -> openAgregarCondicionCrecimientoDialog());   
        
        
        this.onAccept(() -> {});
	}
 
	private void openAgregarCondicionCrecimientoDialog() {
		AgregarCondicionCrecimientoViewModel agregarViewModel = 
				new AgregarCondicionCrecimientoViewModel(repositorioIndicadores);
		AgregarCondicionCrecimientoDialog agregarCondicionDialog 
		= new AgregarCondicionCrecimientoDialog(this, agregarViewModel, condicionesTaxativas);
		agregarCondicionDialog.open();
	}

	private Object openAgregarCondicionOperacionMatematicaDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object openAgregarCondicionCompararEmpresaDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object openAgregarCondicionNAniosDialog() {
		// TODO Auto-generated method stub
		return null;
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
