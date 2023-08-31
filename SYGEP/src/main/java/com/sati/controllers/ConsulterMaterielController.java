package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequetePacours;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ConsulterMaterielController {

	
	@Autowired
	Iservice service;
	
	@Autowired
	RequetePacours requetePacours;
	
	@Autowired
	RequeteMateriel requeteMateriel;
	private Materiel materiel = new Materiel();
	private Parcours parcours = new Parcours();
	private Materiel selectedObject;
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private List<Parcours> listeHistoriqueParcours = new ArrayList<Parcours>();
	//private String code_materiel;
	
	
	public void selectionnerLigne() {
		materiel = selectedObject;
		listeHistoriqueParcours = requetePacours.historiqueParcours(materiel.getIdMateriel());
			}
	
	public void annuler() {
		this.materiel.setCodeMateriel(null);
		this.materiel.setNomMateriel(null);
		this.materiel.setReferenceMateriel(null);
		this.materiel.setNature(null);
		this.listeHistoriqueParcours.clear();
		
		
	}

	
	public List<Parcours> getListeHistoriqueParcours() {
		return listeHistoriqueParcours;
	}

	public void setListeHistoriqueParcours(List<Parcours> listeHistoriqueParcours) {
		this.listeHistoriqueParcours = listeHistoriqueParcours;
	}


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

	public Materiel getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Materiel selectedObject) {
		this.selectedObject = selectedObject;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = requeteMateriel.listerMaterielAvecQRCODE();
		return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}


	
}
