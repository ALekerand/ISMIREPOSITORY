package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Demande;
import com.sati.model.Diagnostique;
import com.sati.model.Materiel;
import com.sati.model.Nature;
import com.sati.model.Parcours;

@Transactional
@Component
@Scope("session")
public class RequeteDiagnostique {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Diagnostique recupererLastDiagnostiqueDuMateriel(int id_materiel) {
		String query = "SELECT `diagnostique`.* FROM `diagnostique` WHERE `diagnostique`.`ID_MATERIEL` = '"+id_materiel+"' ORDER BY `diagnostique`.`ID_DIAGNOSTIQUE`DESC";
		Diagnostique diagnostique = (Diagnostique) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Diagnostique.class).list().get(0);
	return diagnostique;
		}
	
	public List recupererMaterielMauvaisEtat() {
		
		String query = "SELECT `diagnostique`.* FROM `diagnostique` WHERE `diagnostique`.`ID_ETAT` = 2";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Diagnostique.class).list();
		return list;
		
	}
	
	public List materielDefaillant() {
		String query = "SELECT * FROM `diagnostique` WHERE diagnostique.ID_ETAT = '2' ORDER BY `diagnostique`.`ID_DIAGNOSTIQUE`DESC";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Diagnostique.class).list();
		return list;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
