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
public class RequetePacours {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Parcours recupererLastParcoursParMateriel(int id_materiel) {
		String query = "SELECT `parcours`.* FROM `parcours` WHERE `parcours`.`ID_MATERIEL` = '"+id_materiel+"' ORDER BY `parcours`.`ID_PACOURS` DESC";
		Parcours parcours = (Parcours) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Parcours.class).list().get(0);
	return parcours;
		}
	
	
	public List historiqueParcours(int id_materiel) {
		String query = "SELECT * FROM `parcours`,`materiel` WHERE parcours.ID_MATERIEL = '"+id_materiel+"' ORDER BY parcours.DATE_PARCOURS DESC";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Parcours.class).list();
		return list;
	}
	
	public List historiqueMateriel() {
		String query = "SELECT * FROM `parcours`,`materiel` WHERE parcours.ID_MATERIEL = materiel.ID_MATERIEL ORDER BY parcours.DATE_PARCOURS DESC";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Parcours.class).list();
		return list;
	}
	
	
	
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
