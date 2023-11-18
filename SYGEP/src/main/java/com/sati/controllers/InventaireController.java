package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Famille;
import com.sati.model.Inventaire;
import com.sati.model.Nature;
import com.sati.requetes.RequeteInventaire;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class InventaireController {
	@Autowired
	Iservice service;
	@Autowired
	RequeteInventaire requeteInventaire;
	
	private Inventaire inventaire = new Inventaire();
	private List<Inventaire> listTable = new ArrayList<Inventaire>();
	private Inventaire selectedObject = new Inventaire();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnCloturer = new CommandButton();
	private InputText input_code = new InputText();
	private InputText input_lib = new InputText();
	private Calendar date_debut = new org.primefaces.component.calendar.Calendar();
	private Calendar date_fin = new Calendar();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		Inventaire unInventaire = requeteInventaire.lastInventaire();
		// Pas d'existence d'inventaire
		if (unInventaire == null) {
		this.inventaire.setCodeInventaire(genererCodeInventaire());
		this.date_fin.setDisabled(true);
		this.btnCloturer.setDisabled(true);
			
		}else if (unInventaire.getEtatCloture()== true) { //I'inventaire existe mais pas cloturé ou
		// Activer les champs
			this.inventaire.setCodeInventaire(genererCodeInventaire());
			this.input_code.setDisabled(false);
			this.input_lib.setDisabled(false);
			this.date_debut.setDisabled(false);
			this.btnEnregistrer.setDisabled(false);
			this.date_fin.setDisabled(true);
			this.btnCloturer.setDisabled(true);
		}else { 
			inventaire = unInventaire;
			this.input_code.setDisabled(true);
			this.input_lib.setDisabled(true);
			this.date_debut.setDisabled(true);
			this.btnEnregistrer.setDisabled(true);
			this.date_fin.setDisabled(false);
			this.btnCloturer.setDisabled(false);
		}
	}

	public String genererCodeInventaire() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Inventaire").size();
		if(nbEnregistrement < 10)
			prefix = "IV00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "IV0" ;
		if (nbEnregistrement > 100) 
			prefix = "IV" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	public void enregistrer() {
		this.inventaire.setEtatCloture(false);
		service.addObject(inventaire);
		this.info("Enregistrement effectué avec succès!");
 
		this.input_code.setDisabled(true);
		this.input_lib.setDisabled(true);
		this.date_debut.setDisabled(true);
		this.btnEnregistrer.setDisabled(true);
		this.date_fin.setDisabled(false);
		this.btnCloturer.setDisabled(false);
		
		annuler();
		this.inventaire.setCodeInventaire(genererCodeInventaire());
	}
	
	public void cloturerInventaire() {
		inventaire.setEtatCloture(true);
		service.updateObject(inventaire);
		annuler();
		input_code.setDisabled(false);
		input_lib.setDisabled(false);
		date_debut.setDisabled(false);
		this.date_fin.setDisabled(true);
		this.btnCloturer.setDisabled(true);
		this.info("Cloture effectuée avec succès!");
		
	}

	public void selectionnerLigne() {
		this.inventaire = this.selectedObject;
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Contact admin."));
	}

	public void annuler() {
		this.inventaire.setCodeInventaire(null);
		this.inventaire.setLibInventaire(null);
		this.inventaire.setDateDebutInventaire(null);
		this.inventaire.setDateFinInventaire(null);
		initialiser();
		
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		
	}

	public void modifier() {
		this.service.updateObject(this.inventaire);
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

	public Inventaire getInventaire() {
		return inventaire;
	}

	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

	public List<Inventaire> getListTable() {
		return listTable = service.getObjects("Inventaire");
	}

	public void setListTable(List<Inventaire> listTable) {
		this.listTable = listTable;
	}

	public Inventaire getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Inventaire selectedObject) {
		this.selectedObject = selectedObject;
	}

	public CommandButton getBtnCloturer() {
		return btnCloturer;
	}

	public void setBtnCloturer(CommandButton btnCloturer) {
		this.btnCloturer = btnCloturer;
	}

	public InputText getInput_code() {
		return input_code;
	}

	public void setInput_code(InputText input_code) {
		this.input_code = input_code;
	}

	public InputText getInput_lib() {
		return input_lib;
	}

	public void setInput_lib(InputText input_lib) {
		this.input_lib = input_lib;
	}

	public Calendar getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Calendar date_debut) {
		this.date_debut = date_debut;
	}

	public Calendar getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Calendar date_fin) {
		this.date_fin = date_fin;
	}
	
	
}