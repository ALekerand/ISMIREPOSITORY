package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.model.Reparation;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequeteReparation;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class MaterielEnReparationController {
	
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteReparation requeteReparation;
	
	private Reparation reparation = new Reparation();
	private List<Reparation> listMaterielEnReparation = new ArrayList<Reparation>();
	private Reparation selectedReparation = new Reparation();
	
	


	
	public void selectionnerLigne() {
		this.reparation = this.selectedReparation;
	}
	

	@SuppressWarnings("unchecked")
	public List<Reparation> getListMaterielEnReparation() {

		listMaterielEnReparation = requeteReparation.materielEnReparation();
		System.out.println("======Taille de la liste:"+listMaterielEnReparation.size());
		return listMaterielEnReparation;
	}

	public void setListMaterielEnReparation(List<Reparation> listMaterielEnReparation) {
		this.listMaterielEnReparation = listMaterielEnReparation;
	}

	public Reparation getSelectedReparation() {
		return selectedReparation;
	}

	public void setSelectedReparation(Reparation selectedReparation) {
		this.selectedReparation = selectedReparation;
	}


	public Reparation getReparation() {
		return reparation;
	}


	public void setReparation(Reparation reparation) {
		this.reparation = reparation;
	}
	

}
