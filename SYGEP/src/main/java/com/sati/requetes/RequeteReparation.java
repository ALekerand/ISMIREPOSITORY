package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.sati.model.Reparation;

@Transactional
@Component
@Scope("session")
public class RequeteReparation {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List materielEnReparation() {
		String query = "SELECT * FROM `reparation`, `materiel` WHERE reparation.ID_MATERIEL = materiel.ID_MATERIEL AND materiel.EN_REPARATION = '1'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Reparation.class).list();
	return list;
	}
	
	public List materielDejaReparer() {
		String query = "SELECT * FROM `reparation`, `materiel` WHERE reparation.ID_MATERIEL = materiel.ID_MATERIEL AND materiel.EN_REPARATION = '0'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Reparation.class).list();
	return list;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	
	
	
}
