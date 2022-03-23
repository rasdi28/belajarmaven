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
import com.sdd.belajarmaven.domain.Vreportbulan;

public class DetailproductVm {

	private List<Torder> Listdetail;
	private Torder obj;

	private Vreportbulan objdetail;
	
	private Integer bulan;
	private Integer tahun;

	@Wire
	private Grid grid;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("obj") Vreportbulan objdetail) {
		Selectors.wireComponents(view, this, false);

		if (objdetail != null) {
			this.objdetail = objdetail;
			
			bulan = Integer.valueOf(new SimpleDateFormat("MM").format(objdetail.getMonth()));
			tahun = Integer.valueOf(new SimpleDateFormat("yyyy").format(objdetail.getMonth()));
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
	public void doReset() {
		try {
			Listdetail = new TorderDAO().listdetailbulan(bulan, tahun);
			doRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void doRefresh()
	{
		grid.setModel(new ListModelList<Torder>(Listdetail));
	}
	
	
	public List<Torder> getListdetail() {
		return Listdetail;
	}

	public void setListdetail(List<Torder> listdetail) {
		Listdetail = listdetail;
	}

	public Torder getObj() {
		return obj;
	}

	public void setObj(Torder obj) {
		this.obj = obj;
	}

	public Vreportbulan getObjdetail() {
		return objdetail;
	}

	public void setObjdetail(Vreportbulan objdetail) {
		this.objdetail = objdetail;
	}

	public Integer getBulan() {
		return bulan;
	}

	public void setBulan(Integer bulan) {
		this.bulan = bulan;
	}

	public Integer getTahun() {
		return tahun;
	}

	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}
