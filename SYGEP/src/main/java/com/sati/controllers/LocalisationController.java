package com.sati.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequetePacours;
import com.sati.service.Iservice;

@SuppressWarnings({ "unchecked", "unchecked" })
@Component
@Scope("session")
public class LocalisationController {
	@Autowired
	Iservice service;
	@Autowired
	private RequetePacours requetePacours; 
	@Autowired
	private RequeteMateriel requeteMateriel;
	
	private Parcours parcours = new Parcours();
	private String codeMateriel;
	private Materiel materiel = new Materiel();
	
	public Parcours rechercher(){
		materiel = requeteMateriel.recupMateriel(codeMateriel);
		try {
			parcours = requetePacours.recupererLastParcoursParMateriel(materiel.getIdMateriel());
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return parcours;
	}
	
	
	public void annuler() {
		codeMateriel ="";
		parcours.setService(null);
		materiel.setCodeMateriel(null);
		materiel.setNomMateriel(null);
	}

	public String getCodeMateriel() {
		return codeMateriel;
	}

	public void setCodeMateriel(String codeMateriel) {
		this.codeMateriel = codeMateriel;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Parcours getParcours() {
		return parcours;
	}

	public void setParcours(Parcours parcours) {
		this.parcours = parcours;
	}
	

}