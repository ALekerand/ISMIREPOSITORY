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

@Transactional
@Component
@Scope("session")
public class RequeteMateriel {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List listerMaterielSansQRCODE() {
		String query = "SELECT `materiel`.* FROM `materiel` WHERE `materiel`.`CODE_MATERIEL` NOT LIKE 'MTQR%'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
	return list;
		}
	
	
	public List listerMaterielAvecQRCODE() {
		String query = "SELECT `materiel`.* FROM `materiel` WHERE `materiel`.`CODE_MATERIEL` LIKE 'MTQR%'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Materiel.class).list();
	return list;
		}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
