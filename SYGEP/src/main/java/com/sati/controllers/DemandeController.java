package com.sati.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Demande;
import com.sati.model.Entite;
import com.sati.model.Entree;
import com.sati.model.EtatDemande;
import com.sati.model.Fournisseur;
import com.sati.model.Materiel;
import com.sati.model.Personne;
import com.sati.model.SourceFinancement;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class DemandeController {
	@Autowired
	Iservice service;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	private Demande demande = new Demande();
	private Materiel materiel = new Materiel();
	UserAuthentication userAuthentication = new UserAuthentication();
	private List<Demande> listTable = new ArrayList<Demande>();
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private int idMotif;
	private Demande selectedObject = new Demande();
	private Materiel selecteMareriel = new Materiel();
	private int idMatereiel;
	
//	Gestion des bouttons de commande
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		chagerUtilisateur();
		this.demande.setCodeDemande(genererCodeDemande());
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public String genererCodeDemande() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Demande").size();
		if(nbEnregistrement < 10)
			prefix = "CD00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CD0" ;
		if (nbEnregistrement > 100) 
			prefix = "CD" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		Personne personne = new Personne();
		Entite entite = new Entite();
		personne = userAuthentication.getPersonne();
		entite = (Entite) service.getObjectById(personne.getIdEntite(), "Entite");
		
		
		//Charger les elements de la demande
		this.demande.setEntite(entite);
		this.demande.setMateriel(materiel);
		this.demande.setEtatDemande((EtatDemande) service.getObjectById(1, "EtatDemande"));
		this.demande.setDateDemande(new Date());
		
		//Enregister en base
		this.service.addObject(this.demande);
		
		info("Enregistrement effectué avec succès!");
		annuler();
		}
			
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, monMessage, null));
	}

	public void annuler() {
		demande.setCodeDemande(null);
		demande.setMotifDemande(null);
		demande.setQteDemande(null);
		demande.setDateDemande(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
	}
	
	
	public void choisirLigneMateriel() {
		materiel = selecteMareriel;
		}
	
	public void modifier() {
		service.updateObject(demande);
		info("Modification effectuée avec succès!");
	}
	public void selectionnerLigne() {
		this.demande = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public CommandButton getBtnEnregistrer() {
		return this.btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}

	public CommandButton getBtnAnnuler() {
		return this.btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public CommandButton getBtnModifier() {
		return this.btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	
	
	public Demande getDemande() {
		return demande;
	}

	public void setDemande(Demande demande) {
		this.demande = demande;
	}

	
	public int getIdMatereiel() {
		return idMatereiel;
	}

	public void setIdMatereiel(int idMatereiel) {
		this.idMatereiel = idMatereiel;
	}

	@SuppressWarnings("unchecked")
	public List<Demande> getListTable() {
		listTable = service.getObjects("Demande");
		return listTable;
	}

	public void setListTable(List<Demande> listTable) {
		this.listTable = listTable;
	}

	public int getIdMotif() {
		return idMotif;
	}

	public void setIdMotif(int idMotif) {
		this.idMotif = idMotif;
	}

	public Demande getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Demande selectedObject) {
		this.selectedObject = selectedObject;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = service.getObjects("Materiel");
		return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}

	public Materiel getSelecteMareriel() {
		return selecteMareriel;
	}

	public void setSelecteMareriel(Materiel selecteMareriel) {
		this.selecteMareriel = selecteMareriel;
	}


	

	
}