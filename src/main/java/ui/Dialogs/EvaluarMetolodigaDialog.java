package ui.Dialogs;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import domain.Metodologia;
import empresas.Empresa;
import empresas.EmpresaRankeada;
import ui.InviertiendoView;
import ui.ViewModels.AgregarMetodologiaViewModel;
import ui.ViewModels.EvaluarMetodologiaViewModel;


public class EvaluarMetolodigaDialog  extends  Dialog<EvaluarMetodologiaViewModel> {

	public EvaluarMetolodigaDialog(WindowOwner owner, EvaluarMetodologiaViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		
		
		Panel cosasASeleccionarPanel = new Panel(mainPanel).setLayout(new ColumnLayout(3));
		
		Panel tablePanel = new Panel(cosasASeleccionarPanel).setLayout(new VerticalLayout());

		InviertiendoView.agregarLabel(tablePanel, "Empresas");
		
		Table<Empresa> table = new Table<Empresa>(tablePanel, Empresa.class);
		table.bindItemsToProperty("empresas");
		table.setNumberVisibleRows(6);
		table.setWidth(3000);
		table.bindSelectionToProperty("empresaSeleccionada");

		InviertiendoView.agregarColumna(table, "Nombre", "nombre");

		
		Panel tableMetodologiasPanel = new Panel(cosasASeleccionarPanel).setLayout(new VerticalLayout());

		InviertiendoView.agregarLabel(tableMetodologiasPanel , "Metodologias");
		
		Table<Metodologia> tableMeotodologias = new Table<Metodologia>(tableMetodologiasPanel, Metodologia.class);
		tableMeotodologias.bindItemsToProperty("metodologias");
		tableMeotodologias.setNumberVisibleRows(6);
		tableMeotodologias.setWidth(3000);
		tableMeotodologias.bindSelectionToProperty("metodologiaSeleccionada");

		InviertiendoView.agregarColumna(tableMeotodologias, "Nombre", "nombre");

		Panel tableEmpresasAEvaluarPanel = new Panel(cosasASeleccionarPanel).setLayout(new VerticalLayout());

		InviertiendoView.agregarLabel(tableEmpresasAEvaluarPanel , "Empresas A Evaluar");
		
		Table<Empresa> tableEmpresasAEvaluar= new Table<Empresa>(tableEmpresasAEvaluarPanel, Empresa.class);
		tableEmpresasAEvaluar.bindItemsToProperty("empresasAEvaluar");
		tableEmpresasAEvaluar.setNumberVisibleRows(6);
		tableEmpresasAEvaluar.setWidth(3000);
//		tableMeotodologias.bindSelectionToProperty("empresaSeleccionadaAEvaluar");

		InviertiendoView.agregarColumna(tableEmpresasAEvaluar, "Nombre", "nombre");

		InviertiendoView.crearBoton(mainPanel, "Agregar Seleccionada a Empresas a Evaluar").onClick(() -> this.getModelObject().agregarEmpresaAEvaluar());

		InviertiendoView.crearBoton(mainPanel, "Remover de los seleccionados").onClick(() -> this.getModelObject().removerEmpresaAEvaluar());
		
		InviertiendoView.crearBoton(mainPanel, "Evaluar empresas seleccionadas con la metodologia").onClick(() -> this.getModelObject().evaluarEmpresas());
		
		Panel tableEmpresasOrdenadasPanel = new Panel(mainPanel).setLayout(new VerticalLayout());

		InviertiendoView.agregarLabel(tableEmpresasOrdenadasPanel, "Empresas Ordenadas por la metodologia: ");
		
		Table<EmpresaRankeada> tableEmpresasOrdenadas= new Table<EmpresaRankeada>(tableEmpresasOrdenadasPanel, EmpresaRankeada.class);
		tableEmpresasOrdenadas.bindItemsToProperty("empresasRankeadas");
		tableEmpresasOrdenadas.setNumberVisibleRows(6);
		tableEmpresasOrdenadas.setWidth(3000);

		InviertiendoView.agregarColumna(tableEmpresasOrdenadas, "Nombre", "empresa.nombre");
		InviertiendoView.agregarColumna(tableEmpresasOrdenadas, "Ranking", "ranking");
	}

}
