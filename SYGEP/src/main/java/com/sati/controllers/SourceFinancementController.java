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
import com.sati.model.Fonction;
import com.sati.model.Nature;
import com.sati.model.SourceFinancement;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class SourceFinancementController {
	@Autowired
	Iservice service;
	
	private SourceFinancement source = new SourceFinancement();
	private List<SourceFinancement> listTable = new ArrayList<SourceFinancement>();
	private SourceFinancement selectedObject = new SourceFinancement();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		source.setCodeSource(genererCodeSourceFin());
	}
	
	public String genererCodeSourceFin() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("SourceFinancement").size();
		if(nbEnregistrement < 10)
			prefix = "CSF00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CSF0" ;
		if (nbEnregistrement > 100) 
			prefix = "CSF" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		this.service.addObject(this.source);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
		genererCodeSourceFin();
	}

	public void selectionnerLigne() {
		this.source = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,monMessage, null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Contact admin."));
	}

	public void annuler() {
		this.source.setLibSource(null);
		this.source.setDescriptionSource(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.source);
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

	public SourceFinancement getSource() {
		return source;
	}

	public void setSource(SourceFinancement source) {
		this.source = source;
	}

	@SuppressWarnings("unchecked")
	public List<SourceFinancement> getListTable() {
		listTable = service.getObjects("SourceFinancement");
		return listTable;
	}

	public void setListTable(List<SourceFinancement> listTable) {
		this.listTable = listTable;
	}

	public SourceFinancement getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(SourceFinancement selectedObject) {
		this.selectedObject = selectedObject;
	}

	
	
	
}