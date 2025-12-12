package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Demande;
import com.sati.model.Fongible;
import com.sati.model.Materiel;
import com.sati.model.Nature;
import com.sati.model.NonFongible;
import com.sati.model.Parcours;

@Transactional
@Component
@Scope("session")
public class RequeteNonFongible {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List listerNonfongibleNonretires() {
		String query = "SELECT * FROM `non_fongible` WHERE RETRAIT_MATERIEL IS NULL"; 
		List list = sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(NonFongible.class).list();
	return list;
		}
	
	public List listerNonFongibleRetires() {
		String query = "SELECT * FROM `non_fongible` WHERE RETRAIT_MATERIEL = 1";
		List list = sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(NonFongible.class).list();
	return list;
		}
	
	
	
		
}
