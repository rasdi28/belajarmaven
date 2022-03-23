package com.sdd.belajarmaven.viewmodel;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.belajarmaven.dao.TorderDAO;
import com.sdd.belajarmaven.domain.Mproduct;
import com.sdd.belajarmaven.domain.Torder;
import com.sdd.belajarmaven.domain.Vreport;

public class ReportVm {

	private List<Vreport> Listreport;
	private Vreport objreport;
	
	private List<Torder> Listorder;
	
	private Torder objorder;

	@Wire
	private Grid grid;
	
	@Wire
	private Grid detail;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		doReset();
		grid.setRowRenderer(new RowRenderer<Vreport>() {

			@Override
			public void render(Row row, Vreport data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(data.getProductname()));
				row.appendChild(new Label(String.valueOf(data.getTotal())));
				row.appendChild(new Label("Rp."+String.valueOf(data.getSumtotal())));
				Button btnDetail = new Button("Detail");
				btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("obj", data);
					Window win = (Window) Executions.createComponents("/view/detail.zul", null, map);
					win.setWidth("90%");
					win.setClosable(true);
					win.doModal();
						
					}
				});
				
				Hlayout hlayout = new Hlayout();
				hlayout.appendChild(btnDetail);
				row.appendChild(hlayout);
			
			}

		});

	}

	@Command
	@NotifyChange
	public void doReset() {
		try {
			Listreport = new TorderDAO().listfirter();
			doRefresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void doRefresh() {
		grid.setModel(new ListModelList<Vreport>(Listreport));
	}

	public List<Vreport> getListreport() {
		return Listreport;
	}

	public Vreport getObjreport() {
		return objreport;
	}

	public void setListreport(List<Vreport> listreport) {
		Listreport = listreport;
	}

	public void setObjreport(Vreport objreport) {
		this.objreport = objreport;
	}

	public List<Torder> getListorder() {
		return Listorder;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setListorder(List<Torder> listorder) {
		Listorder = listorder;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Grid getDetail() {
		return detail;
	}

	public void setDetail(Grid detail) {
		this.detail = detail;
	}

	public Torder getObjorder() {
		return objorder;
	}

	public void setObjorder(Torder objorder) {
		this.objorder = objorder;
	}

}
