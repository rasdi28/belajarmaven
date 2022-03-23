package com.sdd.belajarmaven.viewmodel;

import java.text.NumberFormat;
import java.util.List;

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

import com.sdd.belajarmaven.dao.MproductDAO;
import com.sdd.belajarmaven.domain.Mproduct;

public class Product1Vm {

	private List<Mproduct> objListProduct;
	private Mproduct objproduct;
	
	private String productname;
	
	@Wire
	private Grid grid;
	
	@Wire
	private Button btnSubmit;
	
	@Wire
	private Button btnDelete;
	
	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		doReset();
		
		grid.setRowRenderer(new RowRenderer<Mproduct>() {

			@Override
			public void render(Row row, Mproduct data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(data.getProductname()));
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getProductstock())));
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getProductprice())));
				
				Button btnEdit = new Button("Edit");
				btnEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
					objproduct = data;
					btnSubmit.setLabel("Update");
					BindUtils.postNotifyChange(null, null, Product1Vm.this, "objproduct");
					btnDelete.setDisabled(false);
					}
				});
				
				Button btnDel = new Button("Delete");
				btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						new MproductDAO().delete(data);
						doReset();	
					}
				});
				
				Hlayout hlayout = new Hlayout();
				hlayout.appendChild(btnEdit);
				hlayout.appendChild(btnDel);
				row.appendChild(hlayout);
				
			}
		});
		
	} 
	
	
	@Command
	@NotifyChange("*")
	public void doReset() {
		productname = null;
		objproduct = new Mproduct();
		objListProduct = new MproductDAO().listAll();
		doRefresh();
		btnSubmit.setLabel("submit");
		btnDelete.setDisabled(true);
	}
	

	public void doRefresh() {
		grid.setModel(new ListModelList<Mproduct>(objListProduct));
	}
	
	@Command
	public void doSearch() {
		if(productname !=null && productname.trim().length()>0) {
			objListProduct = new MproductDAO().getByProductname(productname.trim());
			doRefresh();
		}
	}

	
	@Command
	@NotifyChange("*")
	public void doSubmit() {
		try {
			new MproductDAO().save(objproduct);
		} catch (Exception e) {
			Messagebox.show(e.getMessage());
			e.printStackTrace();
		}
		doReset();
	}
	
	@Command
	@NotifyChange("*")
	public void doDelete() {
		try {
			new MproductDAO().delete(objproduct);
		} catch (Exception e) {
			Messagebox.show(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public List<Mproduct> getObjListProduct() {
		return objListProduct;
	}
	public Mproduct getObjproduct() {
		return objproduct;
	}
	public void setObjListProduct(List<Mproduct> objListProduct) {
		this.objListProduct = objListProduct;
	}
	public void setObjproduct(Mproduct objproduct) {
		this.objproduct = objproduct;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	
	
}
