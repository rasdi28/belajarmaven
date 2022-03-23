package com.sdd.belajarmaven.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vreport {
	

	private Integer mproductfk;
	private Integer total;
	private String productname;
	private Integer sumtotal;
	
	@Id
	public Integer getMproductfk() {
		return mproductfk;
	}
	public Integer getTotal() {
		return total;
	}
	public String getProductname() {
		return productname;
	}
	public void setMproductfk(Integer mproductfk) {
		this.mproductfk = mproductfk;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Integer getSumtotal() {
		return sumtotal;
	}
	public void setSumtotal(Integer sumtotal) {
		this.sumtotal = sumtotal;
	}
	
		
}
