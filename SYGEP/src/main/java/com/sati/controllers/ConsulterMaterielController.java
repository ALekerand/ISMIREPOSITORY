package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.requetes.RequetePacours;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ConsulterMaterielController {

	
	@Autowired
	Iservice service;
	
	@Autowired
	RequetePacours requetePacours;
	
	private Parcours parcours = new Parcours();
	private Materiel materiel = new Materiel();
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private String code_materiel;
	
	
	@SuppressWarnings("unchecked")
	public void ListhistoriqueMateriel() {
		listMateriel = requetePacours.historiqueMateriel(code_materiel);
		annuler();
	}
	
	public void annuler() {
		setCode_materiel(null);
	}

	public List<Materiel> getListMateriel() {
		return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
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



	public String getCode_materiel() {
		return code_materiel;
	}



	public void setCode_materiel(String code_materiel) {
		this.code_materiel = code_materiel;
	}
}
