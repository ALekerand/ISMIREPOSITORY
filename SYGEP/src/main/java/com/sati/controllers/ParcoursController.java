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

import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.model.Service;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequetePacours;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ParcoursController {
	@Autowired
	Iservice service;
	
	private Parcours parcours = new Parcours();
	private Materiel materiel = new Materiel();
	private Materiel selectedObject = new Materiel();
	private Service leService = new Service();
	private int idEntite;
	
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private List<Parcours> listParcours = new ArrayList<Parcours>();
	private List<Service> listService = new ArrayList<Service>();
	
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	
	@Autowired
	private RequeteMateriel requeteMateriel;
	@Autowired
	private RequetePacours requetePacours;
	
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
	}
	
	
	public void selectionnerLigne() {
		this.materiel = this.selectedObject;
		//Charger la position du matériel
		parcours = (Parcours) requetePacours.listerParcoursParMateriel(materiel.getIdMateriel()).get(0);
		this.btnEnregistrer.setDisabled(false);
	}
	
	public void affecterMareriel() {
		leService = (Service) service.getObjectById(idEntite, "Service");
		parcours.setService(leService);
		service.updateObject(parcours);
		annuler();
		info("Affectation de matériel effectuée!");
	}
	
	public void annuler() {
		this.materiel.setCodeMateriel(null);
		this.materiel.setNomMateriel(null);
		parcours.setService(null);
		this.setIdEntite(0);
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}
	

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}
	
	//Getters and Setters
	public Parcours getParcours() {
		return parcours;
	}

	public void setParcours(Parcours parcours) {
		this.parcours = parcours;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public List<Materiel> getListMateriel() {
		listMateriel = requeteMateriel.listerMaterielAvecQRCODE();
		return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}

	public Materiel getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Materiel selectedObject) {
		this.selectedObject = selectedObject;
	}



	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}



	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
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

	public RequeteMateriel getRequeteMateriel() {
		return requeteMateriel;
	}

	public void setRequeteMateriel(RequeteMateriel requeteMateriel) {
		this.requeteMateriel = requeteMateriel;
	}

	public List<Parcours> getListParcours() {
		return listParcours;
	}

	public void setListParcours(List<Parcours> listParcours) {
		this.listParcours = listParcours;
	}

	public int getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}

	public List<Service> getListService() {
		listService = service.getObjects("Service");
		return listService;
	}

	public void setListService(List<Service> listService) {
		this.listService = listService;
	}
	
}
