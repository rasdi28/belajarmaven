package com.sdd.belajarmaven.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.I;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.sdd.belajarmaven.dao.MproductDAO;
import com.sdd.belajarmaven.domain.Mproduct;

public class ProductCreateVm {

	private List<Mproduct> Listproduct;
	private Mproduct objproduct;
	private Boolean isInsert;

	@Wire
	private Grid grid;

	private int count;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objProduct") Mproduct objProduct) {
		Selectors.wireComponents(view, this, false);

		Listproduct = new ArrayList<Mproduct>();
		doReset();
		if (objProduct != null) {
			this.objproduct = objProduct;
			isInsert = false;
		}
	}

	@Command
	public void doCreate() {
		Messagebox.show("Apakah anda akan menyimpan", "Konfirmasi", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {

							try {
								if (isInsert) {
									count++;
									objproduct.setMproductpk(count);
								}
								new MproductDAO().save(objproduct);
								Messagebox.show("data berhasil disimpan");
								doAwal();
							} catch (Exception e) {
								e.printStackTrace();
								Messagebox.show(e.getMessage());
							}
						}
					}
				});
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		objproduct = new Mproduct();
		isInsert = true;
		doRefresh();
	}

	@Command
	@NotifyChange("*")
	public void doRefresh() {
		Listproduct = new MproductDAO().listAll();
		count = Listproduct.size();
	}

	@Command
	@NotifyChange("*")
	public void doAwal() {
		Executions.sendRedirect("product.zul");
	}

	public List<Mproduct> getListproduct() {
		return Listproduct;
	}

	public void setListproduct(List<Mproduct> listproduct) {
		Listproduct = listproduct;
	}

	public Mproduct getObjproduct() {
		return objproduct;
	}

	public void setObjproduct(Mproduct objproduct) {
		this.objproduct = objproduct;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
