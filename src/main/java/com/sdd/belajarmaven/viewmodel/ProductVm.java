package com.sdd.belajarmaven.viewmodel;

import java.text.NumberFormat;
import java.util.ArrayList;
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
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.sdd.belajarmaven.dao.MproductDAO;
import com.sdd.belajarmaven.domain.Mproduct;



public class ProductVm {


	@Wire
	private Grid grid;


	private List<Mproduct> objList;
	private Mproduct objproduct;
	
	
	
	private MproductDAO oDAO = new MproductDAO();

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		objList = new ArrayList<Mproduct>();
		doReset();
		
		grid.setRowRenderer(new RowRenderer<Mproduct>() {

			@Override

			public void render(Row row, Mproduct data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(data.getProductname()));
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getProductstock())));
				row.appendChild(new Label("Rp. "+NumberFormat.getInstance().format(data.getProductprice())));
				
				Button btnDel = new Button("Delete");
				btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
					doDelete(data);
					}
				});
				
				Button btnEdit = new Button("Edit");
				btnEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("objProduct", data);
						Window win = (Window) Executions.createComponents("/view/createproduct.zul", null, map);
						win.setClosable(true);
						win.doModal();
						win.setWidth("50%");
					}
				});
	
				Hbox hbox = new Hbox();
				hbox.appendChild(btnEdit);
				hbox.appendChild(new Separator("horizontal"));
				hbox.appendChild(btnDel);
				row.appendChild(hbox);
				
				
			}
		});
		
	}
	
	
	
	
	
	@Command
	@NotifyChange
	public void doEdit(Mproduct obj) {
		Executions.sendRedirect("createproduct.zul");
	}
	
	
	public void doDelete(Mproduct obj1) {
		Messagebox.show("Apakah anda akan menghapus ?", "Konfirmasi", Messagebox.OK | Messagebox.CANCEL, 
				Messagebox.QUESTION, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if(event.getName().equals("onOK")) {
							try {
								oDAO.delete(obj1);
								Messagebox.show("data berhasil dihapus");
								doReset();
								BindUtils.postNotifyChange(null, null, ProductVm.this, "*");
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
						
					}
				});
	}
	
	public void doReset() {
		objproduct = new Mproduct();
		doRefreshModel();
	}

	public void doRefreshModel() {
		objList = new MproductDAO().listAll();
		grid.setModel(new ListModelList<Mproduct>(objList));
		
	}
	
	public void create() {
		Executions.sendRedirect("createproduct.zul");
	}
	
}
