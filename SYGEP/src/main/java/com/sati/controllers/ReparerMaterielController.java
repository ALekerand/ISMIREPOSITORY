package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Diagnostique;
import com.sati.model.Materiel;
import com.sati.model.Reparation;
import com.sati.requetes.RequeteDiagnostique;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ReparerMaterielController {
	
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteDiagnostique requeteDiagnostique;
	
	
	private Diagnostique diagnostique = new Diagnostique();
	private List<Diagnostique> listMaterielDefaillant = new ArrayList<Diagnostique>();
	private Diagnostique selectedMateriel = new Diagnostique();
	
	private Materiel materiel = new Materiel();
	
	private Reparation reparation = new Reparation();
	private List<Reparation> listMaterielRepare = new ArrayList<Reparation>();
	private Reparation selectedObject;
	
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler =new CommandButton();
	private Calendar date_fin = new Calendar();
	
	
	@PostConstruct
	public void initialiser() {
		
		this.btnModifier.setDisabled(true);
		this.date_fin.setDisabled(true);
		this.reparation.setCode(genererCodeReparation());
	}
	
	public String genererCodeReparation() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Reparation").size();
		if(nbEnregistrement < 10)
			prefix = "CR00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CR0" ;
		if (nbEnregistrement > 100) 
			prefix = "CR" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	public void enregistrer() {
		
		reparation.setMateriel(diagnostique.getMateriel());
		this.service.addObject(reparation);
		
		Materiel materiel = diagnostique.getMateriel();
		materiel.setEnReparation(true);
		materiel.getNonFongible().setEnReparation(true);
		this.service.updateObject(materiel);
		this.service.updateObject(materiel.getNonFongible());
		
		this.info("Enregistrement effectué avec succès!");
		this.reparation.setCode(genererCodeReparation());
		annuler();
		
	}
	public void choisirLigneMateriel() {
		this.diagnostique = this.selectedMateriel;
	}
	
	public void selectionnerLigne() {
		this.reparation = this.selectedObject;
	}


	public void modifier() {
		
		this.service.updateObject(reparation);
		this.info("Madification effectuée avec succès!");
		annuler();
		
	}
	
	public void annuler() {
		
		reparation.setDescriptionPanne(null);
		reparation.setDateMiseReparation(null);
		setSelectedMateriel(null);
		
	}
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Contact admin."));
	}

	public Reparation getReparation() {
		return reparation;
	}

	public void setReparation(Reparation reparation) {
		this.reparation = reparation;
	}



	@SuppressWarnings("unchecked")
	public List<Reparation> getListMaterielRepare() {
		
		listMaterielRepare = service.getObjects("Reparation");
		
		 Collections.sort(listMaterielRepare, new Comparator<Reparation>() {
		        @Override
		        public int compare(Reparation ob1, Reparation ob2)
		        {
		 
		            return  ob1.getMateriel().getNomMateriel().compareTo(ob2.getMateriel().getNomMateriel());
		        }
		    });
		System.out.println("==========Taille dela liste est:"+listMaterielRepare.size());
		return listMaterielRepare;
	}

	public void setListMaterielRepare(List<Reparation> listMaterielRepare) {
		this.listMaterielRepare = listMaterielRepare;
	}

	public Reparation getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Reparation selectedObject) {
		this.selectedObject = selectedObject;
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

	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	@SuppressWarnings("unchecked")
	public List<Diagnostique> getListMaterielDefaillant() {
		
		listMaterielDefaillant = requeteDiagnostique.materielDefaillant();
		
		return listMaterielDefaillant;
	}

	public void setListMaterielDefaillant(List<Diagnostique> listMaterielDefaillant) {
		this.listMaterielDefaillant = listMaterielDefaillant;
	}

	public Diagnostique getDiagnostique() {
		return diagnostique;
	}

	public void setDiagnostique(Diagnostique diagnostique) {
		this.diagnostique = diagnostique;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Diagnostique getSelectedMateriel() {
		return selectedMateriel;
	}

	public void setSelectedMateriel(Diagnostique selectedMateriel) {
		this.selectedMateriel = selectedMateriel;
	}

	public Calendar getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Calendar date_fin) {
		this.date_fin = date_fin;
	}

}
