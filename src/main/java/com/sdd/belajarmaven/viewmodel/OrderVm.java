package com.sdd.belajarmaven.viewmodel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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
import org.zkoss.zk.ui.sys.ObjectPropertyAccess;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.sdd.belajarmaven.dao.MproductDAO;
import com.sdd.belajarmaven.dao.TorderDAO;
import com.sdd.belajarmaven.domain.Mproduct;
import com.sdd.belajarmaven.domain.Torder;

public class OrderVm {

	private List<Torder> Listorder;
	private Torder objorder;
	private BigDecimal total;

	private List<Torder> Listkeranjang = new ArrayList<Torder>();
	private List<Mproduct> listProduct = new ArrayList<>();
	private Map<Integer, Mproduct> mapProduct = new HashMap<Integer, Mproduct>();
	// untuk search
	private String productname;
	private Date ordertime;
	private BigDecimal totalprice;

	@Wire
	private Grid grid;

	@Wire
	private Grid gridkeranjang;

	@Wire
	private Button btnSubmit;

	@Wire
	private Combobox cbproduct;

	@Wire
	private Button btnDelete;

	Mproduct objproduct;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		doReset();

		gridkeranjang.setRowRenderer(new RowRenderer<Torder>() {

			@Override
			public void render(Row row, Torder data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(data.getMproduct().getProductname()));
				row.appendChild(new Label(String.valueOf(data.getOrderqty())));
				row.appendChild(new Label(new SimpleDateFormat("dd-MM-yyyy HH-mm").format(data.getOrdertime())));
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getTotalprice())));

			Button btncancel = new Button("Cancel");
			btncancel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
				
					Listkeranjang.remove(data);
					doRefresh1();
					BindUtils.postNotifyChange(null, null, OrderVm.this, "*");
					
				}
			});
			
			Hlayout hlayout = new Hlayout();
			hlayout.appendChild(btncancel);
			row.appendChild(hlayout);
				
			}

		});

		grid.setRowRenderer(new RowRenderer<Torder>() {

			@Override
			public void render(Row row, Torder data, int index) throws Exception {
				row.appendChild(new Label(String.valueOf(++index)));
				row.appendChild(new Label(data.getMproduct().getProductname()));
				row.appendChild(new Label(String.valueOf(data.getOrderqty())));
				row.appendChild(new Label(new SimpleDateFormat("dd-MM-yyyy HH-mm").format(data.getOrdertime())));
				row.appendChild(new Label(NumberFormat.getInstance().format(data.getTotalprice())));

				Button btnEdit = new Button("Edit");
				btnEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						objorder = data;
						cbproduct.setValue(objorder.getMproduct().getProductname());
						btnSubmit.setLabel("Update");
						BindUtils.postNotifyChange(null, null, OrderVm.this, "*");
						btnDelete.setDisabled(false);

					}
				});

				Button btnDel = new Button("Delete");
				btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.show("apakah anda akan menghapus? ", "Konfirmasi", Messagebox.OK | Messagebox.CANCEL,
								Messagebox.QUESTION, new EventListener<Event>() {

									@Override
									public void onEvent(Event event) throws Exception {
										if (event.getName().equals("onOK")) {
											try {
												new TorderDAO().delete(data);
												Messagebox.show("Data Berhasil dihapus");
												doReset();
												BindUtils.postNotifyChange(null, null, OrderVm.this, "*");

											} catch (Exception e) {
												e.printStackTrace();
												Messagebox.show(e.getMessage());
											}
										}

									}
								});
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
		ordertime = new Date();
		objorder = new Torder();
		Listorder = new TorderDAO().listAll();
		doRefresh();
	}
	
	@Command
	@NotifyChange("*")
	public void doRefresh1() {
		gridkeranjang.setModel(new ListModelList<Torder>(Listkeranjang));
	}

	public void doRefresh() {
		grid.setModel(new ListModelList<Torder>(Listorder));
		
	}

	@NotifyChange("*")
	@Command
	public void doCal(@BindingParam("price") BigDecimal price, @BindingParam("qty") int qty) {
		totalprice = price.multiply(new BigDecimal(qty));
		objorder.setTotalprice(totalprice);
		objorder.setOrderqty(qty);

	}

	@Command
	@NotifyChange("*")
	public void doKeranjang() {
		try {
			objproduct = mapProduct.get(objorder.getMproduct().getMproductpk());

			if (objproduct == null) {
				objproduct = objorder.getMproduct();
			}

			objproduct.setProductstock(objproduct.getProductstock() - objorder.getOrderqty());
			mapProduct.put(objproduct.getMproductpk(), objproduct);

			if (objproduct.getProductstock() > 0) {
				objorder.setOrdertime(ordertime);
				Listkeranjang.add(objorder);
				listProduct.add(objproduct);
				cbproduct.setValue(null);
				doList();
				doReset();
			} else {
				Messagebox.show("Maaf Stoknya Kurang");
				cbproduct.setValue(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doList() {
		gridkeranjang.setModel(new ListModelList<Torder>(Listkeranjang));

	}

	@Command
	@NotifyChange("*")
	public void doSubmit() {
		try {
			objproduct = objorder.getMproduct();
			objproduct.setProductstock(objproduct.getProductstock() - objorder.getOrderqty());
			if (objproduct.getProductstock() > 0) {
				objorder.setOrdertime(ordertime);
				new TorderDAO().save(objorder);
				new MproductDAO().save(objproduct);
				Messagebox.show("Data Berhasil disimpan");
			} else {
				Messagebox.show("data kurang");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			for (Torder data : Listkeranjang) {
				new TorderDAO().save(data);

			}

			for (Entry<Integer, Mproduct> entry : mapProduct.entrySet()) {
				Mproduct data = entry.getValue();
				new MproductDAO().save(data);
				Messagebox.show("Data sudah disimpan");
			}
			doReset();
			doRefreshkeranjang();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Command
	@NotifyChange("*")
	public void doRefreshkeranjang() {
		Listkeranjang = new ListModelList<>();
		gridkeranjang.setModel(new ListModelList<>(Listkeranjang));
	}

	@Command
	@NotifyChange("objorder")
	public void doDelete() {
		try {
			new TorderDAO().delete(objorder);
			Messagebox.show("data berhasil dihapus");
			cbproduct.setValue(null);
			doReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Torder> getListorder() {
		return Listorder;
	}

	public Torder getObjorder() {
		return objorder;
	}

	public void setListorder(List<Torder> listorder) {
		Listorder = listorder;
	}

	public void setObjorder(Torder objorder) {
		this.objorder = objorder;
	}

	public ListModelList<Mproduct> getMproductModel() {
		return new ListModelList<Mproduct>(new MproductDAO().listAll());
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

}
