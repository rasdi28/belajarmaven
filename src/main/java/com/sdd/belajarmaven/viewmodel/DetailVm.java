package com.sdd.belajarmaven.viewmodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.sdd.belajarmaven.dao.TorderDAO;
import com.sdd.belajarmaven.domain.Torder;
import com.sdd.belajarmaven.domain.Vreport;

public class DetailVm {

	
	private List<Torder> Listdetail;
	private Torder obj;
	private Vreport objdetail;
	
	private Integer mproductfk;
	
	
	@Wire
	private Grid grid;
	

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Vreport objdetail) {
		Selectors.wireComponents(view, this, false);
		
		if(objdetail != null) {
			this.objdetail = objdetail;
			doReset();
		}
		
		grid.setRowRenderer(new RowRenderer<Torder>() {
			
			@Override
			public void render(Row row, Torder data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(data.getMproduct().getProductname()));
				row.appendChild(new Label(String.valueOf(data.getOrderqty())));
				row.appendChild(new Label(new SimpleDateFormat("dd-MM-yyyy HH-mm").format(data.getOrdertime())));
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getTotalprice())));
				
			}
		});

	}
	
	@Command
	@NotifyChange
	public void doReset(){
		try {
			Listdetail = new TorderDAO().listdetail(objdetail.getMproductfk());
			doRefresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void doRefresh()
	{
		grid.setModel(new ListModelList<Torder>(Listdetail));
	}
	

	public List<Torder> getListdetail() {
		return Listdetail;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setListdetail(List<Torder> listdetail) {
		Listdetail = listdetail;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}
