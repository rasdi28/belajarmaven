package com.sdd.belajarmaven.basic;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;

public class PrimitiveVm {

	private String name ="rasdi";
	List<Integer> hitung;
	private Integer number= 1;

	private List<String> kolom;
	
	
	@Wire
	Grid grid;
	


	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	
		for(int i=1; i<10; i++) {
			
			
			
		}
		
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	public List<Integer> getHitung() {
		return hitung;
	}
	public List<String> getKolom() {
		return kolom;
	}


	public void setKolom(List<String> kolom) {
		this.kolom = kolom;
	}


	public void setHitung(List<Integer> hitung) {
		this.hitung = hitung;
	}
	
}
