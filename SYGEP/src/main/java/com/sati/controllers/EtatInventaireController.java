package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.EtatInventaire;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class EtatInventaireController {
	
	
	@Autowired
	Iservice service;
	private EtatInventaire etatInventaire = new EtatInventaire();
	private List<EtatInventaire> listEtatInventaire = new ArrayList<>();
	private EtatInventaire selectedEtatInventaire;
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		
	}
	
	public void enregistrer() {
		service.addObject(etatInventaire);
		this.info("Enregistrement effectué avec succès!");
		annuler();
	}
	public void modifier() {
		service.updateObject(etatInventaire);
		this.info("Modification effectuée avec succès!");
	}
	
	public void annuler() {
		etatInventaire.setLibEtatInventaire(null);
	}
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}
	
	public void selectionnerLigne() {
		this.etatInventaire = selectedEtatInventaire;
	}
	public EtatInventaire getEtatInventaire() {
		return etatInventaire;
	}
	public void setEtatInventaire(EtatInventaire etatInventaire) {
		this.etatInventaire = etatInventaire;
	}
	
	@SuppressWarnings("unchecked")
	public List<EtatInventaire> getListEtatInventaire() {
		listEtatInventaire = service.getObjects("EtatInventaire");
		System.out.println("=========Taille de la liste:"+listEtatInventaire.size());
		return listEtatInventaire;
	}
	public void setListEtatInventaire(List<EtatInventaire> listEtatInventaire) {
		this.listEtatInventaire = listEtatInventaire;
	}
	public EtatInventaire getSelectedEtatInventaire() {
		return selectedEtatInventaire;
	}
	public void setSelectedEtatInventaire(EtatInventaire selectedEtatInventaire) {
		this.selectedEtatInventaire = selectedEtatInventaire;
	}

	public CommandButton getBtnEnregistrer() {
		return btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

}
