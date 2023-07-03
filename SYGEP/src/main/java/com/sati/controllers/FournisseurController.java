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
import com.sati.model.Fournisseur;
import com.sati.model.Nature;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class FournisseurController {
	@Autowired
	Iservice service;
	
	private Fournisseur fournisseur = new Fournisseur();
	private List<Fournisseur> listTable = new ArrayList<Fournisseur>();
	private Fournisseur selectedObject = new Fournisseur();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		fournisseur.setCodeFournisseur(genererCodeFournisseur());
	}
	
	public String genererCodeFournisseur() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Fournisseur").size();
		if(nbEnregistrement < 10)
			prefix = "CF00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CF0" ;
		if (nbEnregistrement > 100) 
			prefix = "CF" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		this.service.addObject(this.fournisseur);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
		//Generer � nouveau le code du fournisseur
		fournisseur.setCodeFournisseur(genererCodeFournisseur());
	}

	public void selectionnerLigne() {
		this.fournisseur = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,monMessage,null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.fournisseur.setCodeFournisseur(null);
		this.fournisseur.setNomFournisseur(null);
		this.fournisseur.setNccFournisseur(null);
		this.fournisseur.setAdresseFournisseur(null);
		this.fournisseur.setTelephoneFournisseur(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.fournisseur);
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

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	

	public Fournisseur getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Fournisseur selectedObject) {
		this.selectedObject = selectedObject;
	}

	@SuppressWarnings("unchecked")
	public List<Fournisseur> getListTable() {
		listTable = service.getObjects("Fournisseur");
		System.out.println("=================Taille de laliste: "+listTable.size());
		return listTable;
	}

	public void setListTable(List<Fournisseur> listTable) {
		this.listTable = listTable;
	}

}