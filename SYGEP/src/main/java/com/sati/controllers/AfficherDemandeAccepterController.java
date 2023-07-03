package com.sati.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

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
public class AfficherDemandeAccepterController {
	
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
		selectedObject.setEtatReception(true);
		selectedObject.setDateEtatReception(new Date());
		service.updateObject(selectedObject);
		annuler();
	}
	
	public void annuler() {
		setSelectedObject(null);
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
