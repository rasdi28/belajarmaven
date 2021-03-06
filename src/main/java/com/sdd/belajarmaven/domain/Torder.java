package com.sdd.belajarmaven.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Torder {

	private Integer torderpk;
	private Integer orderqty;
	private Date ordertime;
	private BigDecimal totalprice;
	private Mproduct mproduct;
	
	
	
	@Id
	@SequenceGenerator(name = "TORDER_SEQ", sequenceName = "TORDER_SEQ", allocationSize = 1, initialValue = 100)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MPRODUCT_SEQ")
	public Integer getTorderpk() {
		return torderpk;
	}
	
	public Integer getOrderqty() {
		return orderqty;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOrdertime() {
		return ordertime;
	}
	public BigDecimal getTotalprice() {
		return totalprice;
	}
	public void setTorderpk(Integer torderpk) {
		this.torderpk = torderpk;
	}
	
	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	
	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}
	
	
	
	
}
