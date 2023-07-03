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

import com.sati.model.EtatDemande;
import com.sati.model.Famille;
import com.sati.model.Nature;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class EtatDemandeController {
	@Autowired
	Iservice service;
	
	private EtatDemande etatDemande = new EtatDemande();
	private List<EtatDemande> listTable = new ArrayList<EtatDemande>();
	private EtatDemande selectedObject = new EtatDemande();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		this.etatDemande.setCodeEtatDemande(genererCodeEtatDemande());
	}
	
	public String genererCodeEtatDemande() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("EtatDemande").size();
		if(nbEnregistrement < 10)
			prefix = "CED00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CED0" ;
		if (nbEnregistrement > 100) 
			prefix = "CED" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		this.service.addObject(this.etatDemande);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
		this.genererCodeEtatDemande();
	}

	public void selectionnerLigne() {
		this.etatDemande = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void annuler() {
		this.etatDemande.setCodeEtatDemande(null);
		this.etatDemande.setLibEtatDemande(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		info("Annulation effectuée avec succès!");
	}
	
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Contact admin."));
	}

	public void modifier() {
		this.service.updateObject(this.etatDemande);
		this.info("Modification effectué avec succès!");
		this.annuler();
	}

	public CommandButton getBtnEnregistrer() {
		return this.btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}

	public CommandButton getBtnAnnuler() {
		return this.btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public CommandButton getBtnModifier() {
		return this.btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public EtatDemande getEtatDemande() {
		return etatDemande;
	}

	public void setEtatDemande(EtatDemande etatDemande) {
		this.etatDemande = etatDemande;
	}

	public EtatDemande getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(EtatDemande selectedObject) {
		this.selectedObject = selectedObject;
	}

	@SuppressWarnings("unchecked")
	public List<EtatDemande> getListTable() {
	 listTable = service.getObjects("EtatDemande");
	 return listTable;
	}

	public void setListTable(List<EtatDemande> listTable) {
		this.listTable = listTable;
	}
}