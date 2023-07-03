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

import com.sati.model.Caracteristique;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class CaracteristiqueController {
	@Autowired
	Iservice service;
	
	private Caracteristique caracteristique = new Caracteristique();
	private List<Caracteristique> listTable = new ArrayList<Caracteristique>();
	private Caracteristique selectedObject = new Caracteristique();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		genererCode();
	}
	
	public void genererCode() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Caracteristique").size();
		if(nbEnregistrement < 10)
			prefix = "CC00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CC0" ;
		if (nbEnregistrement > 100) 
			prefix = "CC" ;
		caracteristique.setCodeCaracteristique(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		this.service.addObject(this.caracteristique);
		genererCode();
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}

	public void selectionnerLigne() {
		this.caracteristique = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"Contactez l'administrateur", null));
	}

	public void annuler() {
		this.caracteristique.setCodeCaracteristique(null);
		this.caracteristique.setLibCaracteristique(null);
		this.caracteristique.setDescriptionCaracteristique(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
	}

	public void modifier() {
		this.service.updateObject(this.caracteristique);
		this.annuler();
		genererCode();
		this.info("Modification effectué avec succès!");
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

	public Caracteristique getCaracteristique() {
		return caracteristique;
	}

	public void setCaracteristique(Caracteristique caracteristique) {
		this.caracteristique = caracteristique;
	}

	@SuppressWarnings("unchecked")
	public List<Caracteristique> getListTable() {
		listTable = service.getObjects("Caracteristique");
		return listTable;
	}

	public void setListTable(List<Caracteristique> listTable) {
		this.listTable = listTable;
	}

	public Caracteristique getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Caracteristique selectedObject) {
		this.selectedObject = selectedObject;
	}
	
	
}