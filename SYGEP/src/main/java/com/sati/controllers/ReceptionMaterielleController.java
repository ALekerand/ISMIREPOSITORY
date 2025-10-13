package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Demande;
import com.sati.model.Entite;
import com.sati.model.Personne;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteDemande;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ReceptionMaterielleController {
	
	@Autowired
	RequeteDemande requeteDemande;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	@Autowired
	Iservice service;
	private Demande demande = new Demande();
	UserAuthentication userAuthentication = new UserAuthentication();
	private List<Demande> listDemandeAccepter = new ArrayList<Demande>();
	private Demande selectedObject = new Demande();
	private int idEntite;

	@PostConstruct
	public void initialiser() {
		chagerUtilisateur();
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public void selectionnerLigne() {
		this.demande = this.selectedObject;
		
	}
	
	public void receptionner() {
		try {
			selectedObject.setEtatReception(true);
			selectedObject.setDateEtatReception(new Date());
			service.updateObject(selectedObject);
			annuler();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			error("Veuillez au préalable selectionner la ligne concernée");
		}
	}
	
	public void annuler() {
		setSelectedObject(null);
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, monMessage, null));
	}
	
	
	
	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}
	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
	public Demande getDemande() {
		return demande;
	}
	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	@SuppressWarnings("unchecked")
	public List<Demande> getListDemandeAccepter() {
		Personne personne = new Personne();
		Entite entite = new Entite();
		personne = userAuthentication.getPersonne();
		entite = (Entite) service.getObjectById(personne.getIdEntite(), "Entite");
		setIdEntite(entite.getIdEntite());
		listDemandeAccepter = requeteDemande.afficherDemandeAccepte(idEntite);
		
		  Collections.sort(listDemandeAccepter, new Comparator<Demande>() {
		        @Override
		        public int compare(Demande ob1, Demande ob2)
		        {
		 
		            return  ob1.getEntite().getPersonne().getNomPersonne().compareTo(ob2.getEntite().getPersonne().getNomPersonne());
		        }
		    });
		return listDemandeAccepter;
		
	}
	public void setListDemandeAccepter(List<Demande> listDemandeAccepter) {
		this.listDemandeAccepter = listDemandeAccepter;
	}
	public int getIdEntite() {
		return idEntite;
	}
	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}

	public Demande getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Demande selectedObject) {
		this.selectedObject = selectedObject;
	}

}
