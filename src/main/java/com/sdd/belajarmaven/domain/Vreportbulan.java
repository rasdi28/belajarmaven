package com.sdd.belajarmaven.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Vreportbulan {

	
	private Date month;
	private Integer total;
	private Integer sumtotal;
	



	public Integer getTotal() {
		return total;
	}
	
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	@Id
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public Integer getSumtotal() {
		return sumtotal;
	}
	public void setSumtotal(Integer sumtotal) {
		this.sumtotal = sumtotal;
	}
	
}
