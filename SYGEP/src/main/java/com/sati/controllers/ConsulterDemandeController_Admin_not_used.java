package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Demande;
import com.sati.model.EtatDemande;
import com.sati.requetes.RequeteDemande;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ConsulterDemandeController_Admin_not_used {
	
	@Autowired
	Iservice service;
	//@Autowired
	//RequeteUtilisateur requeteUtilisateur;
	@Autowired
	RequeteDemande requeteDemande;
	

	private int idEtatDemande;
	private List<Demande> listeDemande = new ArrayList<Demande>();
	private List<EtatDemande> listEtatDemande = new ArrayList<EtatDemande>();
	private List<Demande> listeDemandesAttente = new ArrayList<Demande>();
	
	
	
	
	
	public void traiterDemande() {
		if(idEtatDemande ==0) {
			listeDemande = service.getObjects("Demande");
			
		}else {
			listeDemande = requeteDemande.afficherDemande_Admin(idEtatDemande);
		}
		
	}
	
	public void annuler() {
		listeDemande.clear();
	}
	
	
	
	
	
	public void push(Severity seviry, String summary, String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(seviry, monMessage ,null ));
		
	}



	public int getIdEtatDemande() {
		return idEtatDemande;
	}

	public void setIdEtatDemande(int idEtatDemande) {
		this.idEtatDemande = idEtatDemande;
	}



	public List<EtatDemande> getListEtatDemande() {
		return listEtatDemande = service.getObjects("EtatDemande");
	}



	public void setListEtatDemande(List<EtatDemande> listEtatDemande) {
		this.listEtatDemande = listEtatDemande;
	}



	public List<Demande> getListeDemande() {
		return listeDemande;
	}



	public void setListeDemande(List<Demande> listeDemande) {
		this.listeDemande = listeDemande;
	}

	public List<Demande> getListeDemandesAttente() {
		return listeDemandesAttente;
	}

	public void setListeDemandesAttente(List<Demande> listeDemandesAttente) {
		this.listeDemandesAttente = listeDemandesAttente;
	}

}