package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.model.Reparation;

import com.sati.requetes.RequeteReparation;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class RetourEnServiceController {
	
	@Autowired
	Iservice service;
	
	@Autowired 
	RequeteReparation requeteReparation;
	
	private Reparation reparation = new Reparation();
	private List<Reparation> listReparation = new ArrayList<Reparation>();
	private Reparation selectedMateriel;
	
	private List<Reparation> listMaterielEnReparation = new ArrayList<Reparation>();
	private Materiel materiel = new Materiel();
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	
	
	public void enregistrer() {
		
	
		service.updateObject(reparation);
		
		Materiel materiel = reparation.getMateriel();
		materiel.setEnReparation(false);
		this.service.updateObject(materiel);
		this.info("Enregistrement effectué avec succès!");
		annuler();
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}
	
	
	public void choisirLigneMateriel() {
		this.reparation = this.selectedMateriel;
	}
	
	public void annuler() {
		reparation.setCode(null);
		reparation.setDateMiseReparation(null);
		reparation.setDateRetourService(null);
		reparation.setDescriptionPanne(null);
		materiel.setCodeMateriel(null);
		materiel.setNomMateriel(null);
		
		
	}
	
	public Reparation getReparation() {
		return reparation;
	}

	public void setReparation(Reparation reparation) {
		this.reparation = reparation;
	}

	@SuppressWarnings("unchecked")
	public List<Reparation> getListReparation() {
		listReparation = requeteReparation.materielDejaReparer();
		
		  Collections.sort(listReparation, new Comparator<Reparation>() {
		        @Override
		        public int compare(Reparation ob1, Reparation ob2)
		        {
		 
		            return  ob1.getMateriel().getNomMateriel().compareTo(ob2.getMateriel().getNomMateriel());
		        }
		    });
		System.out.println("========Taille de la liste:"+listReparation.size());
		return listReparation;
	}

	public void setListReparation(List<Reparation> listReparation) {
		this.listReparation = listReparation;
	}

	public Reparation getSelectedMateriel() {
		return selectedMateriel;
	}

	public void setSelectedMateriel(Reparation selectedMateriel) {
		this.selectedMateriel = selectedMateriel;
	}

	@SuppressWarnings("unchecked")
	public List<Reparation> getListMaterielEnReparation() {
		
		listMaterielEnReparation = requeteReparation.materielEnReparation();
		return listMaterielEnReparation;
	}

	public void setListMaterielEnReparation(List<Reparation> listMaterielEnReparation) {
		this.listMaterielEnReparation = listMaterielEnReparation;
	}

	public CommandButton getBtnEnregistrer() {
		return btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}

	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

}
