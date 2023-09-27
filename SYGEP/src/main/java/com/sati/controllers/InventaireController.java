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
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Inventaire;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class InventaireController {

	
	@Autowired
	Iservice service;
	private Inventaire inventaire = new Inventaire();
	private List<Inventaire> listInventaire = new ArrayList<Inventaire>();
	private Inventaire selectedInventaire;
	private Date dateDebut;
	private Date dateFin;
	private String etat;
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private org.primefaces.component.calendar.Calendar input_date_fin = new org.primefaces.component.calendar.Calendar();
	private SelectOneRadio radio_etat = new SelectOneRadio();
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		this.input_date_fin.setDisabled(true);
		this.radio_etat.setValue("non");
		inventaire.setCodeInventaire(genererCodeInventaire());
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
	
	public void gererDateFin() {
		if (etat.equals("oui")) {
			this.input_date_fin.setDisabled(false);
		}else {
			this.input_date_fin.setDisabled(true);
		}
	}
	public void enregistrer() {
		
		SimpleDateFormat formateurDate1 = new SimpleDateFormat("yyyy-MM-dd");
		String dateD = formateurDate1.format(dateDebut);
		inventaire.setDateDebutInventaire(dateDebut);
		if (etat.equals("non")) {
			inventaire.setEtatCloture(false);
		}else {
			inventaire.setEtatCloture(true);
		}
		service.addObject(inventaire);
		inventaire.setCodeInventaire(genererCodeInventaire());
		this.info("Enregistrement efectué avec succès!");
		annuler();
		
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

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public SelectOneRadio getRadio_etat() {
		return radio_etat;
	}

	public void setRadio_etat(SelectOneRadio radio_etat) {
		this.radio_etat = radio_etat;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public org.primefaces.component.calendar.Calendar getInput_date_fin() {
		return input_date_fin;
	}

	public void setInput_date_fin(org.primefaces.component.calendar.Calendar input_date_fin) {
		this.input_date_fin = input_date_fin;
	}
}
