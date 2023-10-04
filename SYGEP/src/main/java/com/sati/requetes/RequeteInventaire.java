package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Inventaire;

@Transactional
@Component
@Scope("session")
public class RequeteInventaire {

	@Autowired
	
	private SessionFactory sessionFactory;

	public List recupererLastInventaire() {
		String query = "SELECT * FROM inventaire ORDER BY inventaire.DATE_FIN_INVENTAIRE DESC LIMIT 1";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inventaire.class).list();
		return list;
	}
	
	public Inventaire lastInventaire() {
		String query = "SELECT * FROM inventaire ORDER BY inventaire.CODE_INVENTAIRE DESC LIMIT 1";
		Inventaire inventaire = (Inventaire) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inventaire.class).uniqueResult();
		return inventaire;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
