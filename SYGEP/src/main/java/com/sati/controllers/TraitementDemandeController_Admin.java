package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
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
public class TraitementDemandeController_Admin {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteDemande requeteDemande;
	private Demande demande = new Demande();
	private List<Demande> listTable = new ArrayList<Demande>();
	private List<Demande> listeDemande = new ArrayList<Demande>();
	private List<EtatDemande> listEtatDemande = new ArrayList<EtatDemande>();
	private Demande selectedObject = new Demande();
	private int idEtatDemande;
	

	@SuppressWarnings("unchecked")
	public void traiterDemande() {
		listeDemande = requeteDemande.afficherDemande_Admin(idEtatDemande);
		
	}
	
	
	public void selectionnerLigne() {
		this.demande = this.selectedObject;
		
	}
	
	
	public void validerDemande() {
		selectedObject.setEtatDemande((EtatDemande)service.getObjectById(2, "EtatDemande"));
		service.updateObject(selectedObject);
		info("Demande validée");
		
		annuler();
	}

	public void rejeterDemande() {
		selectedObject.setEtatDemande((EtatDemande)service.getObjectById(3, "EtatDemande"));
		service.updateObject(selectedObject);
		info("Demande rejetée");
        
        annuler();
	}
	
	public void annuler() {
		setSelectedObject(null);
		setIdEtatDemande(0);
		setListeDemande(null);
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
	public List<Demande> getListTable() {
		listTable = requeteDemande.traiterEtatDemande();
		return listTable;
	}
	public void setListTable(List<Demande> listTable) {
		this.listTable = listTable;
	}
	public Demande getSelectedObject() {
		return selectedObject;
	}
	public void setSelectedObject(Demande selectedObject) {
		this.selectedObject = selectedObject;
	}

	public List<Demande> getListeDemande() {
		return listeDemande;
	}


	public void setListeDemande(List<Demande> listeDemande) {
		this.listeDemande = listeDemande;
	}


	@SuppressWarnings("unchecked")
	public List<EtatDemande> getListEtatDemande() {
		listEtatDemande = service.getObjects("EtatDemande");
		return listEtatDemande;
	}


	public void setListEtatDemande(List<EtatDemande> listEtatDemande) {
		this.listEtatDemande = listEtatDemande;
	}
	
	
	
	public int getIdEtatDemande() {
		return idEtatDemande;
	}


	public void setIdEtatDemande(int idEtatDemande) {
		this.idEtatDemande = idEtatDemande;
	}



}
