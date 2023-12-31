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

import com.sati.model.Famille;
import com.sati.model.Nature;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class NatureController {
	@Autowired
	Iservice service;
	
	private Nature nature = new Nature();
	private List<Nature> listTable = new ArrayList<Nature>();
	private List<Famille> listFamille = new ArrayList<Famille>();
	private Nature selectedObject = new Nature();
	private Famille famille = new Famille();
	private int idFamille;
	
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
		int nbEnregistrement = this.service.getObjects("Nature").size();
		if(nbEnregistrement < 10)
			prefix = "NA00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "NA0" ;
		if (nbEnregistrement > 100) 
			prefix = "NA" ;
		this.nature.setCodeNature(prefix+(nbEnregistrement+1));
	}
	
	public void enregistrer() {
		nature.setFamille(famille);
		this.service.addObject(nature);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}

	public void chargerFamille() {
		System.out.println("=====ID FAMILLE ======="+idFamille);
		famille = (Famille) service.getObjectById(idFamille, "Famille");//A terminer
	}
	
	public void selectionnerLigne() {
		this.nature = this.selectedObject;
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
		this.nature.setCodeNature(null);
		this.nature.setLibNature(null);
		this.nature.setDescriptionNature(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		
	}

	public void modifier() {
		this.service.updateObject(this.nature);
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

	public Nature getNature() {
		genererCode();
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	@SuppressWarnings("unchecked")
	public List<Nature> getListTable() {
		 listTable = service.getObjects("Nature");
		 return listTable;
	}

	public void setListTable(List<Nature> listTable) {
		this.listTable = listTable;
	}

	public Nature getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Nature selectedObject) {
		this.selectedObject = selectedObject;
	}

	public List<Famille> getListFamille() {
		listFamille = service.getObjects("Famille");
		return listFamille;
	}

	public void setListFamille(List<Famille> listFamille) {
		this.listFamille = listFamille;
	}

	public int getIdFamille() {
		return idFamille;
	}

	public void setIdFamille(int idFamille) {
		this.idFamille = idFamille;
	}
}