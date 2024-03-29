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

import com.sati.model.Famille;
import com.sati.model.Fonction;
import com.sati.model.Nature;
import com.sati.model.Service;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class FonctionController {
	@Autowired
	Iservice service;
	
	private Fonction fonction = new Fonction();
	private List<Fonction> listTable = new ArrayList<Fonction>();
	private Fonction selectedObject = new Fonction();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		this.fonction.setCodeFonction(genererCodeFonction());
	}
	
	public String genererCodeFonction() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Fonction").size();
		if(nbEnregistrement < 10)
			prefix = "FO00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "FO0" ;
		if (nbEnregistrement > 100) 
			prefix = "FO" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		this.service.addObject(this.fonction);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
		fonction.setCodeFonction(genererCodeFonction());
		
		
	}

	public void selectionnerLigne() {
		this.fonction = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,monMessage ,null ));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Contact admin."));
	}

	public void annuler() {
		this.fonction.setCodeFonction(null);
		this.fonction.setLibFonction(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
	}

	public void modifier() {
		this.service.updateObject(this.fonction);
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

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	@SuppressWarnings("unchecked")
	public List<Fonction> getListTable() {
		listTable = service.getObjects("Fonction");
		
		 Collections.sort(listTable, new Comparator<Fonction>() {
		        @Override
		        public int compare(Fonction ob1, Fonction ob2)
		        {
		 
		            return  ob1.getLibFonction().compareTo(ob2.getLibFonction());
		        }
		    });
		return listTable;
	}
	public void setListTable(List<Fonction> listTable) {
		this.listTable = listTable;
	}

	public Fonction getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Fonction selectedObject) {
		this.selectedObject = selectedObject;
	}
	
	
}