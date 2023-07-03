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

import com.sati.model.Entite;
import com.sati.model.Famille;
import com.sati.model.Nature;
import com.sati.model.Service;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ServiceController {
	@Autowired
	Iservice service;
	
	private Entite entite = new Entite();
	private Service serviceObj = new Service();
	private String codeEntite;
	@SuppressWarnings("unused")
	private List<Service> listTable = new ArrayList<Service>();
	private Service selectedObject = new Service();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		genererCodeEntite();
	}
	
	
	public void genererCodeEntite() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Entite").size();
		if(nbEnregistrement < 10)
			prefix = "ET00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "ET0" ;
		if (nbEnregistrement > 100) 
			prefix = "ET" ;
		codeEntite = prefix+(nbEnregistrement+1);
		System.out.println("======="+codeEntite);
	}

	public void enregistrer() {
		//Enregistrement de l'entit� et du service
		this.entite.setCodeEntite(codeEntite);
		this.entite.setEmail(serviceObj.getEmail());
		this.entite.setTelephone(codeEntite);
		this.entite.setTelephone(serviceObj.getTelephone());
		this.service.addObject(entite);
		
		this.serviceObj.setEntite(entite);
		this.serviceObj.setCodeEntite(codeEntite);
		this.service.addObject(this.serviceObj);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}

	public void selectionnerLigne() {
		this.serviceObj = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage , "Info"));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		
		this.serviceObj.setCodeEntite(null);
		this.serviceObj.setTelephone(null);
		this.serviceObj.setEmail(null);
		this.serviceObj.setNomService(null);
		info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.serviceObj);
		this.info("Modification effectué avec succès!");
		this.annuler();
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		this.btnAnnuler.setDisabled(false);
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

	public Iservice getService() {
		return service;
	}

	public void setService(Iservice service) {
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	public List<Service> getListTable() {
		 listTable = service.getObjects("Service");
		 return listTable;
	}

	public void setListTable(List<Service> listTable) {
		this.listTable = listTable;
	}

	public Service getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Service selectedObject) {
		this.selectedObject = selectedObject;
	}

	public Service getServiceObj() {
		return serviceObj;
	}

	public void setServiceObj(Service serviceObj) {
		this.serviceObj = serviceObj;
	}


	public String getCodeEntite() {
		genererCodeEntite();
		return codeEntite;
	}


	public void setCodeEntite(String codeEntite) {
		this.codeEntite = codeEntite;
	}

}