package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Diagnostique;
import com.sati.model.Materiel;
import com.sati.model.NonFongible;
import com.sati.requetes.RequeteDiagnostique;
import com.sati.requetes.RequeteMateriel;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ConsulterListDiagnosticController {

	
	@Autowired
	Iservice service;
	@Autowired
	RequeteDiagnostique requeteDiagnostique;
	@Autowired
	RequeteMateriel requeteMateriel;
	private List<NonFongible> listMateriel = new ArrayList<NonFongible>();
	private Diagnostique diagnostique;
	private List<Diagnostique> listDiagnostique = new ArrayList<Diagnostique>();
	private int idMateriel;
	
	
	@SuppressWarnings("unchecked")
	public void listMateriel() {
		listMateriel = requeteMateriel.listerMaterielAvecQRCODE();
		
		
		diagnostique = requeteDiagnostique.recupererLastDiagnostiqueDuMateriel(idMateriel);
		
		for(NonFongible materiel : listMateriel) {
			diagnostique = requeteDiagnostique.recupererLastDiagnostiqueDuMateriel(materiel.getIdMateriel());
			listDiagnostique.add(diagnostique);
		}
	}
	
	
	public List<NonFongible> getListMateriel() {
		return listMateriel;
	}
	public void setListMateriel(List<NonFongible> listMateriel) {
		this.listMateriel = listMateriel;
	}
	public List<Diagnostique> getListDiagnostique() {
		return listDiagnostique;
	}
	public void setListDiagnostique(List<Diagnostique> listDiagnostique) {
		this.listDiagnostique = listDiagnostique;
	}

	
	
}

