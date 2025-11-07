package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Demande;
import com.sati.model.Entite;
import com.sati.model.EtatDemande;
import com.sati.model.Personne;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteDemande;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ConsulterDemandeController_User {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteDemande requeteDemande;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	
	private Demande demande = new Demande();
	private UserAuthentication userAuthentication = new UserAuthentication();
	private List<Demande> listeDemandesAttente = new ArrayList<Demande>();
	private List<Demande> listeDemandesTraitees = new ArrayList<Demande>();
	private List<Demande> listeDemandesRejetees = new ArrayList<Demande>();

	

	@PostConstruct
	public void initialiser() {
		chagerUtilisateur();
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage ,null ));
		
	}
	public Demande getDemande() {
		return demande;
	}
	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	
	@SuppressWarnings("unchecked")
	public List<Demande> getListeDemandesAttente() {
		 listeDemandesAttente = requeteDemande.afficherDemande_UtilisateurParEtat(1,chagerUtilisateur().getPersonne().getIdEntite());
		
		  Collections.sort(listeDemandesAttente, new Comparator<Demande>() {
		        @Override
		        public int compare(Demande ob1, Demande ob2)
		        {
		 
		            return  ob1.getEntite().getPersonne().getNomPersonne().compareTo(ob2.getEntite().getPersonne().getNomPersonne());
		        }
		    });
		  
		  return listeDemandesAttente;
	}
	public void setListeDemandesAttente(List<Demande> listeDemandesAttente) {
		this.listeDemandesAttente = listeDemandesAttente;
	}
	@SuppressWarnings("unchecked")
	public List<Demande> getListeDemandesTraitees() {
		 listeDemandesTraitees = requeteDemande.afficherDemande_UtilisateurParEtat(2,chagerUtilisateur().getPersonne().getIdEntite());
		 
		  Collections.sort(listeDemandesTraitees, new Comparator<Demande>() {
		        @Override
		        public int compare(Demande ob1, Demande ob2)
		        {
		 
		            return  ob1.getEntite().getPersonne().getNomPersonne().compareTo(ob2.getEntite().getPersonne().getNomPersonne());
		        }
		    });
		 
		 return listeDemandesTraitees;
	}
	public void setListeDemandesTraitees(List<Demande> listeDemandesTraitees) {
		this.listeDemandesTraitees = listeDemandesTraitees;
	}
	@SuppressWarnings("unchecked")
	public List<Demande> getListeDemandesRejetees() {
		listeDemandesRejetees = requeteDemande.afficherDemande_UtilisateurParEtat(3,chagerUtilisateur().getPersonne().getIdEntite());
		
		  Collections.sort(listeDemandesRejetees, new Comparator<Demande>() {
		        @Override
		        public int compare(Demande ob1, Demande ob2)
		        {
		 
		            return  ob1.getEntite().getPersonne().getNomPersonne().compareTo(ob2.getEntite().getPersonne().getNomPersonne());
		        }
		    });
		  
		  return listeDemandesRejetees;
	}
	public void setListeDemandesRejetees(List<Demande> listeDemandesRejetees) {
		this.listeDemandesRejetees = listeDemandesRejetees;
	}

}