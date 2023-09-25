package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Demande;
import com.sati.model.Materiel;
import com.sati.model.Nature;
import com.sati.model.Parcours;

@Transactional
@Component
@Scope("session")
public class RequeteMateriel {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List listerMaterielSansQRCODE() {
		String query = "SELECT `materiel`.* FROM `materiel` WHERE `materiel`.`CODE_MATERIEL` NOT LIKE 'MTQR%' AND `materiel`.RETRAIT =0 ";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
	return list;
		}
	
	
	public List listerMaterielAvecQRCODE() {
		String query = "SELECT `materiel`.* FROM `materiel` WHERE `materiel`.`RETRAIT` = 0  AND `materiel`.`CODE_MATERIEL` LIKE 'MTQR%'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
	return list;
		}

	public List stockAlertMateriel() {
		String query = "SELECT * FROM fongible WHERE STOCK_ACTUEL <= STOCK_ALERTE";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
		return list;
	}
	
	public List listerMateriel() {
		String query = "SELECT `materiel`.* FROM `materiel` WHERE  `materiel`.RETRAIT =0";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
	return list;
		}
	
	
	public List listeRetraitMateriel() {
		String query = "SELECT `materiel`.* FROM `materiel` WHERE  `materiel`.RETRAIT =1 ";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
	return list;
		}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
