package com.sdd.belajarmaven.viewmodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.text.DateFormats;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.belajarmaven.dao.TorderDAO;
import com.sdd.belajarmaven.domain.Torder;
import com.sdd.belajarmaven.domain.Vreportbulan;

public class ReportbulananVm {

	private List<Vreportbulan> Listreportbulanan;

	private Torder objreportbulan;

	@Wire
	private Grid grid;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		doReset();

		grid.setRowRenderer(new RowRenderer<Vreportbulan>() {

			@Override
			public void render(Row row, Vreportbulan data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(new SimpleDateFormat("yyyy").format(data.getMonth())));
				row.appendChild(new Label(new SimpleDateFormat("MMMMMM").format(data.getMonth())));
				
				A newa = new A(NumberFormat.getInstance().format(data.getTotal()));
				newa.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("obj", data);
						Window win = (Window) Executions.createComponents( "/view/detailproduct.zul", null, map);
						win.setWidth("90%");
						win.setClosable(true);
						win.doModal();
					}
				});
				
				row.appendChild(newa);
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getSumtotal())));

			}
		});

	}

	@Command

	public void doReset() {
		try {
			Listreportbulanan = new TorderDAO().listreportbulanan();
			doRefresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doRefresh() {
		grid.setModel(new ListModelList<Vreportbulan>(Listreportbulanan));
	}

}
