package ui.Dialogs;

import java.util.ArrayList;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import condiciones.Condicion;
import repositorios.RepositorioIndicadores;
import ui.InviertiendoView;
import ui.ViewModels.AgregarCondicionCrecimientoViewModel;
import ui.ViewModels.AgregarCondicionViewModel;


public class AgregarCondicionDialog extends  Dialog<AgregarCondicionViewModel> {

	ArrayList<Condicion> condicionesYaAgregadas;
	RepositorioIndicadores repositorioIndicadores;
	
	
	public AgregarCondicionDialog(WindowOwner owner, AgregarCondicionViewModel model, ArrayList<Condicion> condicionesYaAgregadas, RepositorioIndicadores repositorioIndicadores) {
		super(owner, model);
		this.condicionesYaAgregadas = condicionesYaAgregadas;
		this.repositorioIndicadores = repositorioIndicadores;
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		
		new Label(form).setText("Seleccione el tipo de condicion deseada");
		
        form.setLayout(new ColumnLayout(2));
        
        InviertiendoView.crearBoton(mainPanel, "Un indicador sea mayor o menor a cierto valor, "
        		+ "en el último año o durante los últimos N años"
        		).onClick(() -> openAgregarCondicionNAniosDialog());   
        
        InviertiendoView.crearBoton(mainPanel, "un indicador sea mayor o menor que el de otra empresa"
        		).onClick(() -> openAgregarCondicionCompararEmpresaDialog());   
        
        InviertiendoView.crearBoton(mainPanel, "un promedio, mediana o sumatoria de un cierto "
        		+ "indicador sea mayor o menor a cierto valor"
        		).onClick(() -> openAgregarCondicionOperacionMatematicaDialog());   
        
        InviertiendoView.crearBoton(mainPanel, "un indicador sea sea siempre o casi siempre creciente o "
        		+ "decreciente durante un período"
        		).onClick(() -> openAgregarCondicionCrecimientoDialog());   
        
        
        this.onAccept(() -> {});
	}
 
	private void openAgregarCondicionCrecimientoDialog() {
		AgregarCondicionCrecimientoViewModel agregarViewModel = 
				new AgregarCondicionCrecimientoViewModel(repositorioIndicadores);
		AgregarCondicionCrecimientoDialog agregarCondicionDialog 
		= new AgregarCondicionCrecimientoDialog(this, agregarViewModel, condicionesYaAgregadas);
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
