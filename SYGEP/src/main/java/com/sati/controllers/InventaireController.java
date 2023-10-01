package com.sati.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Inventaire;
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
	private Inventaire lastInventaire = new Inventaire();
	private List<Inventaire> listInventaire = new ArrayList<Inventaire>();
	private Inventaire selectedInventaire;
	private String codeInventaire;
	private String libInventaire;
	private Date dateDebutInventaire;
	private String etat;
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnCloturer = new CommandButton();
	private InputText input_code = new InputText();
	private InputText input_lib = new InputText();
	private org.primefaces.component.calendar.Calendar date_debut = new org.primefaces.component.calendar.Calendar();
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		setCodeInventaire(genererCodeInventaire());
		inventaire = requeteInventaire.lastInventaire();
		if (inventaire.getEtatCloture() != null) {
			inventaire = new Inventaire();
			inventaire.setCodeInventaire(genererCodeInventaire());
			
		}else {
			// Desactiver les champs
			input_code.setDisabled(true);
			input_lib.setDisabled(true);
			date_debut.setDisabled(true);
		}

	}
	
	public String genererCodeInventaire() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Inventaire").size();
		if(nbEnregistrement < 10)
			prefix = "CI00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CI0" ;
		if (nbEnregistrement > 100) 
			prefix = "CI" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	public void enregistrer() {
		service.addObject(inventaire);
		this.info("Enregistrement effectué avec succès!");
		
		
	}
	
	public void cloturerInventaire() {
		inventaire.setEtatCloture(true);
		service.updateObject(inventaire);
		this.info("Cloture effectuée avec succès!");
		annuler();
		input_code.setDisabled(false);
		input_lib.setDisabled(false);
		date_debut.setDisabled(false);
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}
	public void selectionnerLigne() {
		
		this.inventaire = this.selectedInventaire;
	}
	
	public void modifier() {
		
		service.updateObject(inventaire);
		this.info("Modification effectuée avec succès!");
		
	}
	
	public void annuler() {
		inventaire.setCodeInventaire(genererCodeInventaire());
		inventaire.setLibInventaire(null);
		inventaire.setDateDebutInventaire(null);
		inventaire.setDateFinInventaire(null);
		
	}
	public Inventaire getInventaire() {
		return inventaire;
	}
	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}
	
	@SuppressWarnings("unchecked")
	public List<Inventaire> getListInventaire() {
		listInventaire = service.getObjects("Inventaire");
		System.out.println("========Taille de la liste:"+listInventaire.size());
		return listInventaire;
	}
	public void setListInventaire(List<Inventaire> listInventaire) {
		this.listInventaire = listInventaire;
	}
	public Inventaire getSelectedInventaire() {
		return selectedInventaire;
	}
	public void setSelectedInventaire(Inventaire selectedInventaire) {
		this.selectedInventaire = selectedInventaire;
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

	public String getLibInventaire() {
		return libInventaire;
	}

	public void setLibInventaire(String libInventaire) {
		this.libInventaire = libInventaire;
	}

	public Date getDateDebutInventaire() {
		return dateDebutInventaire;
	}

	public void setDateDebutInventaire(Date dateDebutInventaire) {
		this.dateDebutInventaire = dateDebutInventaire;
	}

	public String getCodeInventaire() {
		return codeInventaire;
	}

	public void setCodeInventaire(String codeInventaire) {
		this.codeInventaire = codeInventaire;
	}

	public Inventaire getLastInventaire() {
		return lastInventaire;
	}

	public void setLastInventaire(Inventaire lastInventaire) {
		this.lastInventaire = lastInventaire;
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

	public org.primefaces.component.calendar.Calendar getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(org.primefaces.component.calendar.Calendar date_debut) {
		this.date_debut = date_debut;
	}

}
