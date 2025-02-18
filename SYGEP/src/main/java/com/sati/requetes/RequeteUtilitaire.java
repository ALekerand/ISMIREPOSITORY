package com.sati.requetes;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Entree;

@Transactional
@Component
@Scope("session")
public class RequeteUtilitaire {

	@Autowired
	private SessionFactory sessionFactory;
	
	public int nbrLigne(String nomTable) {
		String query = "SELECT * FROM " +nomTable; 
		int nombreLigne = (int) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Entree.class).list().size();
	return nombreLigne;
		}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
