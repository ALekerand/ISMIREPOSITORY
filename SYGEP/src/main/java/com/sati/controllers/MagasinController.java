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
import com.sati.model.Magasin;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class MagasinController {
	@Autowired
	Iservice service;
	private Magasin  magasin = new Magasin();
	private List<Magasin> listTable = new ArrayList<Magasin>();
	private Magasin selectedObject = new Magasin();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
	}

	public void enregistrer() {
		this.service.addObject(this.magasin);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}

	public void selectionnerLigne() {
		this.magasin = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.magasin.setNomMagasin(null);
		this.magasin.setTelephoneMagasin(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.magasin);
		this.info("Modification effectuée avec succ�s!");
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

	@SuppressWarnings("unchecked")
	public List<Magasin> getListTable() {
		listTable = service.getObjects("Magasin");
		System.out.println("=================Taille de laliste: "+listTable.size());
		return listTable;
	}

	public void setListTable(List<Magasin> listTable) {
		this.listTable = listTable;
	}

	public Magasin getMagasin() {
		return magasin;
	}

	public void setMagasin(Magasin magasin) {
		this.magasin = magasin;
	}

	public Magasin getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Magasin selectedObject) {
		this.selectedObject = selectedObject;
	}

}