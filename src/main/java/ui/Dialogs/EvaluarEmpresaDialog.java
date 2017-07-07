package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import domain.Indicador;
import empresas.Empresa;
import ui.InviertiendoView;
import ui.ViewModels.AgregarIndicadorViewModel;
import ui.ViewModels.EvaluarEmpresaViewModel;

public class EvaluarEmpresaDialog extends Dialog<EvaluarEmpresaViewModel> {

	public EvaluarEmpresaDialog(WindowOwner owner, EvaluarEmpresaViewModel model) {
		super(owner, model);
		this.setTitle("Evaluar Indicador");
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
	  	Panel form = new Panel(mainPanel);
        form.setLayout(new ColumnLayout(2));
        
        new Label(form).setText("Indique el periodo a evaluar");
        new TextBox(form).setWidth(100).bindValueToProperty("periodo");
        
        Panel formEmpresa = new Panel(mainPanel);
        formEmpresa.setLayout(new ColumnLayout(1));
        
        
        new Label(formEmpresa).setText("Empresa evaluada : " + this.getModelObject().getEmpresaSeleccionada().getNombre());
        
        Panel resultadoPanel = new Panel(mainPanel);
        resultadoPanel.setLayout(new ColumnLayout(2));
        
        new Label(resultadoPanel).setText("Resultado :" );
        new Label(resultadoPanel).bindValueToProperty("resultado");
        
        
        Panel seleccionarPanel = new Panel(mainPanel);
        
        new Label(seleccionarPanel).setText("Seleccionar indicador");
		
        Panel indicadorSeleccionadoPanel = new Panel(mainPanel);
        indicadorSeleccionadoPanel.setLayout(new ColumnLayout(2));
        new Label(indicadorSeleccionadoPanel).setText("Formula del indicador ");
        new Label(indicadorSeleccionadoPanel).bindValueToProperty("indicadorSeleccionado.nombre");
        new Label(indicadorSeleccionadoPanel).setText("  ");
        new Label(indicadorSeleccionadoPanel).bindValueToProperty("indicadorSeleccionado.formula");
        
        Panel tablePanel = new Panel(mainPanel).setLayout(new HorizontalLayout());
        
        
       
        
		Table<Indicador> table = new Table<Indicador>(tablePanel, Indicador.class);
		table.bindItemsToProperty("repositorioIndicadores.lista");
		table.setNumberVisibleRows(6);
		table.setWidth(5000);
		table.bindSelectionToProperty("indicadorSeleccionado");
		
		
		
		InviertiendoView.agregarColumna(table, "Nombre", "nombre");
        
		Panel evaluarPanel = new Panel(mainPanel);
        InviertiendoView.crearBoton(evaluarPanel, "Evaluar").onClick(() ->{ this.getModelObject().resolverIndicador();});

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
