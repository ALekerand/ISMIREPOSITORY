package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Demande;
import com.sati.model.Nature;

@Transactional
@Component
@Scope("session")
public class RequeteNature {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List listerNatureParFamille(int idFamille) {
		String query = "SELECT `nature`.* FROM `nature` WHERE `nature`.`ID_FAMILLE` = '"+idFamille+"'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Nature.class).list();
		return list;
		}

	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
