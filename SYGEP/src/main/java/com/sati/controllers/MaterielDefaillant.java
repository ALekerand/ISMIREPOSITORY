package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Diagnostique;
import com.sati.model.Materiel;
import com.sati.requetes.RequeteDiagnostique;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class MaterielDefaillant {
	
	@Autowired
	
	Iservice iservice;
	
	@Autowired
	RequeteDiagnostique requeteDiagnostique;
	
	private Materiel materiel = new Materiel();
	private Diagnostique diagnostique = new Diagnostique();
	private List<Diagnostique> listMaterielDefaillant = new ArrayList<Diagnostique>();

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Diagnostique getDiagnostique() {
		return diagnostique;
	}

	public void setDiagnostique(Diagnostique diagnostique) {
		this.diagnostique = diagnostique;
	}

	@SuppressWarnings("unchecked")
	public List<Diagnostique> getListMaterielDefaillant() {
		
		listMaterielDefaillant = requeteDiagnostique.materielDefaillant();
		System.out.println("========Taille de liste est"+listMaterielDefaillant.size());
		
		  Collections.sort(listMaterielDefaillant, new Comparator<Diagnostique>() {
		        @Override
		        public int compare(Diagnostique ob1, Diagnostique ob2)
		        {
		 
		            return  ob1.getMateriel().getNomMateriel().compareTo(ob2.getMateriel().getNomMateriel());
		        }
		    });
		return listMaterielDefaillant;
	}

	public void setListMaterielDefaillant(List<Diagnostique> listMaterielDefaillant) {
		this.listMaterielDefaillant = listMaterielDefaillant;
	}


}
