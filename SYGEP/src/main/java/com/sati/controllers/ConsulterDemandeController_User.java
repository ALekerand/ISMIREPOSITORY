package com.sati.controllers;

import java.util.ArrayList;
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
	
	public List<Demande> getListeDemandesAttente() {
		return listeDemandesAttente = requeteDemande.afficherDemande_Utilisateur(1,chagerUtilisateur().getPersonne().getIdEntite());
	}
	public void setListeDemandesAttente(List<Demande> listeDemandesAttente) {
		this.listeDemandesAttente = listeDemandesAttente;
	}
	public List<Demande> getListeDemandesTraitees() {
		return listeDemandesTraitees = requeteDemande.afficherDemande_Utilisateur(2,chagerUtilisateur().getPersonne().getIdEntite());
	}
	public void setListeDemandesTraitees(List<Demande> listeDemandesTraitees) {
		this.listeDemandesTraitees = listeDemandesTraitees;
	}
	public List<Demande> getListeDemandesRejetees() {
		return listeDemandesRejetees = requeteDemande.afficherDemande_Utilisateur(3,chagerUtilisateur().getPersonne().getIdEntite());
	}
	public void setListeDemandesRejetees(List<Demande> listeDemandesRejetees) {
		this.listeDemandesRejetees = listeDemandesRejetees;
	}

}