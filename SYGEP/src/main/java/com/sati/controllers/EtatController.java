package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Etat;
import com.sati.model.EtatDemande;
import com.sati.model.Famille;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class EtatController {
	@Autowired
	Iservice service;
	private Etat etat = new Etat();
	private List<Etat> listEtat = new ArrayList<Etat>();
	private Etat selectedObject = new Etat();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		this.etat.setCodeEtat(genererCodeEtat());
	}
	
	
	public String genererCodeEtat() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Etat").size();
		if(nbEnregistrement < 10)
			prefix = "ETD00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "ETD0" ;
		if (nbEnregistrement > 100) 
			prefix = "ETD" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		this.service.addObject(this.etat);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
		this.etat.setCodeEtat(genererCodeEtat());
	
	}

	public void selectionnerLigne() {
		this.etat = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", monMessage));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.etat.setCodeEtat(null);
		this.etat.setLibEtat(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
	}

	public void modifier() {
		this.service.updateObject(this.etat);
		this.annuler();
		this.info("Modification effectuée avec succès!");
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

	public Etat getEtat() {
		this.etat.setCodeEtat(genererCodeEtat());
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	@SuppressWarnings("unchecked")
	public List<Etat> getListEtat() {
		 listEtat = service.getObjects("Etat");
		 Collections.sort(listEtat, new Comparator<Etat>() {
		        @Override
		        public int compare(Etat ob1, Etat ob2)
		        {
		 
		            return  ob1.getLibEtat().compareTo(ob2.getLibEtat());
		        }
		    });
		return listEtat;
	}

	public void setListEtat(List<Etat> listEtat) {
		this.listEtat = listEtat;
	}

	public Etat getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Etat selectedObject) {
		this.selectedObject = selectedObject;
	}

	
}