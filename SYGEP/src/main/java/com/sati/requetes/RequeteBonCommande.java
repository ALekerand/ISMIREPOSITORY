package com.sati.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sati.model.Boncommande;
import com.sati.model.LigneCommande;



@Component
@Scope("session")
@Transactional
public class RequeteBonCommande {



	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List consulterBonCommande( String codeBonCommande) {
	String	query = "SELECT * FROM ligne_commande, boncommande WHERE ligne_commande.ID_BON_COMMANDE = boncommande.ID_BON_COMMANDE AND boncommande.CODE_BON_COMMANDE ='"+codeBonCommande+"'";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(LigneCommande.class).list();
	return list;
	}

	public List listBonCommande() {
		String query = "SELECT * FROM `boncommande` WHERE ID_BON_LIVRAISON IS NULL";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Boncommande.class).list();
		return list;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	
}
