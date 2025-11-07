package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.inputtext.InputText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Demande;
import com.sati.model.EtatDemande;
import com.sati.model.Fongible;
import com.sati.model.Materiel;
import com.sati.model.SourceFinancement;
import com.sati.requetes.RequeteDemande;
import com.sati.requetes.RequeteMateriel;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class TraitementDemandeController_Admin {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteDemande requeteDemande;
	
	@Autowired
	RequeteMateriel requeteMateriel;
	private Demande demande = new Demande();
	private List<Demande> listTable = new ArrayList<Demande>();
	private List<Demande> listeDemande = new ArrayList<Demande>();
	private List<EtatDemande> listEtatDemande = new ArrayList<EtatDemande>();
	private Demande selectedObject = new Demande();
	private int idEtatDemande;
	private Materiel materielSelectionne = new Materiel();
	private Fongible materielFongible = new Fongible();
	
	
	private InputText input_stockAlert = new InputText();
	private InputText input_stockActuel = new InputText();
	private InputText input_reference = new InputText();
/*
 * 
 * 
 * 
	@SuppressWarnings("unchecked")
	public void traiterDemande() {

	}
	*/
	

  public void selectionnerLigne() { 
	  System.out.println("==== Selection ");
	  this.demande = this.selectedObject; 
	  materielSelectionne = this.demande.getMateriel();
	  
	  //Verifier si il est un fongible alors gerer son stock
	  if(this.demande.getMateriel().getFongible()!= null) {
		  System.out.println("==== ========== FONGIBLE ==============");
		  materielFongible = requeteMateriel.materielFongibleConsulte(this.demande.getMateriel().getIdMateriel()); 
		  this.input_stockAlert.setDisabled(false);
		  this.input_stockActuel.setDisabled(false);
		  this.input_reference.setDisabled(true); 
	  }else{ 
		  setMaterielFongible(new Fongible()); 
		  this.input_stockAlert.setDisabled(true);
		  this.input_stockActuel.setDisabled(true);
		  this.input_reference.setDisabled(false); 
		  } 
	  }
	  
	
	
/*
 * public void selectionnerLigne() {
 * 
 * }
 */
	
	public void ok() {
		
	}
	public void validerDemande() {
		try {
			selectedObject.setEtatDemande((EtatDemande)service.getObjectById(2, "EtatDemande"));
			service.updateObject(selectedObject);
			info("Demande validée");
			annuler();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			error("Veuillez au préalable selectionner la ligne concernée");
		}
	}

	public void rejeterDemande() {
		try {
			selectedObject.setEtatDemande((EtatDemande)service.getObjectById(3, "EtatDemande"));
			service.updateObject(selectedObject);
			info("Demande rejetée");
			annuler();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			error("Veuillez au préalable selectionner la ligne concernée");
		}
	}
	
	public void annuler() {
		setSelectedObject(null);
		setIdEtatDemande(0);
		setListeDemande(null);
	}
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage ,null ));
		
	}
	
	
	public void error(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, monMessage, null));
	}
	
	
	
	public Demande getDemande() {
		return demande;
	}
	public void setDemande(Demande demande) {
		this.demande = demande;
	}

	@SuppressWarnings("unchecked")
	public List<Demande> getListTable() {
		listTable = requeteDemande.traiterEtatDemande();
		
		 Collections.sort(listTable, new Comparator<Demande>() {
		        @Override
		        public int compare(Demande ob1, Demande ob2)
		        {
		 
		            return  ob1.getEntite().getPersonne().getNomPersonne().compareTo(ob2.getEntite().getPersonne().getNomPersonne());
		        }
		    });
		return listTable;
	}
	public void setListTable(List<Demande> listTable) {
		this.listTable = listTable;
	}
	public Demande getSelectedObject() {
		return selectedObject;
	}
	public void setSelectedObject(Demande selectedObject) {
		this.selectedObject = selectedObject;
	}

	@SuppressWarnings("unchecked")
	public List<Demande> getListeDemande() {
		listeDemande = requeteDemande.afficherDemande_Admin(idEtatDemande);
		//materiel = (Materiel) service.getObjectById(selectedObject.getMateriel().getIdMateriel(), "Materiel");
		//demande = (Demande) service.getObjectById(selectedObject.getIdDemande(), "Demande");
		return listeDemande;
	}


	public void setListeDemande(List<Demande> listeDemande) {
		this.listeDemande = listeDemande;
	}


	@SuppressWarnings("unchecked")
	public List<EtatDemande> getListEtatDemande() {
		listEtatDemande = service.getObjects("EtatDemande");
		return listEtatDemande;
	}


	public void setListEtatDemande(List<EtatDemande> listEtatDemande) {
		this.listEtatDemande = listEtatDemande;
	}
	
	
	
	public int getIdEtatDemande() {
		return idEtatDemande;
	}


	public void setIdEtatDemande(int idEtatDemande) {
		this.idEtatDemande = idEtatDemande;
	}

	public Materiel getMaterielSelectionne() {
		return materielSelectionne;
	}

	public void setMaterielSelectionne(Materiel materielSelectionne) {
		this.materielSelectionne = materielSelectionne;
	}

	public Fongible getMaterielFongible() {
		return materielFongible;
	}

	public void setMaterielFongible(Fongible materielFongible) {
		this.materielFongible = materielFongible;
	}

	public InputText getInput_stockAlert() {
		return input_stockAlert;
	}

	public void setInput_stockAlert(InputText input_stockAlert) {
		this.input_stockAlert = input_stockAlert;
	}

	public InputText getInput_stockActuel() {
		return input_stockActuel;
	}

	public void setInput_stockActuel(InputText input_stockActuel) {
		this.input_stockActuel = input_stockActuel;
	}

	public InputText getInput_reference() {
		return input_reference;
	}

	public void setInput_reference(InputText input_reference) {
		this.input_reference = input_reference;
	}




}
