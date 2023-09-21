package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.requetes.RequeteMateriel;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class RetraitMaterielController {

	
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteMateriel requeteMateriel;
	
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private List<Materiel> listRetraitMateriel = new ArrayList<Materiel>();
	private Materiel selectedMateriel;
	private Materiel materiel = new Materiel();

	
	
	public void selectionnerLigne() {
		this.materiel = this.selectedMateriel;
	}
	
	public void retirer() {
		selectedMateriel.setRetrait(true);
		service.updateObject(selectedMateriel);
		info("Retrait effectué avec succès!");
		annuler();
	}
	
	public void annuler() {
		setSelectedMateriel(null);
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
	}
	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = requeteMateriel.listerMateriel();
		return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListRetraitMateriel() {
		listRetraitMateriel = requeteMateriel.listeRetraitMateriel();
		return listRetraitMateriel;
	}

	public void setListRetraitMateriel(List<Materiel> listRetraitMateriel) {
		this.listRetraitMateriel = listRetraitMateriel;
	}

	public Materiel getSelectedMateriel() {
		return selectedMateriel;
	}

	public void setSelectedMateriel(Materiel selectedMateriel) {
		this.selectedMateriel = selectedMateriel;
	}
	public Materiel getMateriel() {
		return materiel;
	}
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
}
