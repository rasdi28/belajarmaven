package com.sdd.belajarmaven.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.sdd.belajarmaven.domain.Mproduct;
import com.sdd.belajarmaven.domain.Torder;
import com.sdd.belajarmaven.domain.Vreport;
import com.sdd.belajarmaven.domain.Vreportbulan;

public class TorderDAO {

	SessionFactory sesionFactory = new Configuration().configure().buildSessionFactory();
	Session session;

	@SuppressWarnings("unchecked")
	public List<Torder> listAll() {
		List<Torder> objList = null;
		session = sesionFactory.openSession();
		objList = session.createQuery("from Torder").list();
		session.close();
		return objList;
	}

	@SuppressWarnings("unchecked")
	public List<Mproduct> listByName(String name) {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		List<Mproduct> objList = session.createSQLQuery("select * from Mproduct where squadname like '%" + name + "%'")
				.addEntity(Mproduct.class).list();
		session.close();
		return objList;
	}

	public void save(Torder objorder) {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		Transaction trx = session.beginTransaction();
		session.saveOrUpdate(objorder);
		trx.commit();
		session.close();
	}

	public void delete(Torder objorder) {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		Transaction trx = session.beginTransaction();
		session.delete(objorder);
		trx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Vreport> listfirter() throws Exception {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		List<Vreport> objList = session.createSQLQuery(
				"select mproductfk, productname, sum(orderqty) as total, SUM(totalprice) as sumtotal from torder join mproduct on mproductfk=mproductpk group by mproductfk, productname ")
				.addEntity(Vreport.class).list();
		session.close();
		return objList;
	}

	@SuppressWarnings("unchecked")
	public List<Torder> listdetail(Integer mproductfk) throws Exception {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		List<Torder> objList = session.createSQLQuery("select * from Torder where mproductfk=" + mproductfk)
				.addEntity(Torder.class).list();
		session.close();
		return objList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Vreportbulan> listreportbulanan() throws Exception {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		List<Vreportbulan> objListbulanan = session.createSQLQuery(
				"select  date_trunc('month',ordertime) AS  month , sum(orderqty) as total, SUM(totalprice) as sumtotal from torder join mproduct on mproductfk=mproductpk group by date_trunc('month',ORDERTIME)")
				.addEntity(Vreportbulan.class).list();
		session.close();
		return objListbulanan;
	}
	
	@SuppressWarnings("unchecked")
	public List<Torder> listdetailbulan(Integer bulan, Integer tahun) throws Exception {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		List<Torder> objList = session.createSQLQuery("select * from torder where EXTRACT(MONTH FROM ordertime)="+bulan+" and EXTRACT(YEAR FROM ordertime)="+tahun)
				.addEntity(Torder.class).list();
		session.close();
		return objList;
	}

}
