package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Demande;

@Transactional
@Component
@Scope("session")
public class RequeteDemande {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	public List traiterEtatDemande() {
		String query = "SELECT `demande`.* FROM `demande` WHERE `demande`.`ID_ETAT_DEMANDE` = '1'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Demande.class).list();
		return list;
		}

	
	public List afficherDemande_Admin(int idEtatDemande) {
		String query = "SELECT * FROM demande WHERE ID_ETAT_DEMANDE = '"+idEtatDemande+"'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Demande.class).list();
		return list;
		}
	
	public List afficherDemande_Utilisateur(int idEtatDemande,int idEntite) {
		String query = "SELECT * FROM demande WHERE ID_ETAT_DEMANDE = '"+idEtatDemande+"' AND ID_ENTITE = '"+idEntite+"' ";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Demande.class).list();
		return list;
		}
	
	public List afficherDemandeAccepte(int idEntite) {
		String query = "SELECT * FROM `demande` WHERE ETAT_RECEPTION IS NULL AND`demande`.`ID_ETAT_DEMANDE` = '2' AND ID_ENTITE = '"+idEntite+"'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Demande.class).list();
		return list;
		}
	
	public List afficherDemandeReceptionner() {
		String query = "SELECT * FROM demande WHERE ID_SORTIE IS NULL AND ETAT_RECEPTION = TRUE AND ID_ETAT_DEMANDE = '2'";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Demande.class).list();
		return list;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
