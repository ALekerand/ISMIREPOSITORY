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

import com.sati.model.Entree;
import com.sati.model.Fongible;
import com.sati.model.Fournisseur;
import com.sati.model.Materiel;
import com.sati.model.SourceFinancement;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class EntreeController {
	@Autowired
	Iservice service;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	private Entree entree = new Entree();
	private Materiel  materiel ;//= new Materiel();
	private Fongible fongible = new Fongible();
	private UserAuthentication userAuthentication = new UserAuthentication();
	private List<Fournisseur> listFournisseur = new ArrayList<Fournisseur>();
	private int idFournisseur;
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private List<Entree> listEntree = new ArrayList<Entree>();
	private List<SourceFinancement> listSourceFinance = new ArrayList<SourceFinancement>();
	private int idMateriel;
	//private int stockMateriel;
	private int idSource;
	
//	Gestion des bouttons de commande
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		genererCodeEntree();
		chagerUtilisateur();
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public String genererCodeEntree() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Entree").size();
		if(nbEnregistrement < 10)
			prefix = "CE00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CE0" ;
		if (nbEnregistrement > 100) 
			prefix = "CE" ;
		return new String(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		entree.setMateriel(materiel);
		this.entree.setCodeEntre(genererCodeEntree());
		this.entree.setDateEntree(new Date());
		this.entree.setDateEnregistrement(new Date());
		this.entree.setSourceFinancement((SourceFinancement)service.getObjectById(idSource, "SourceFinancement"));
		this.entree.setPersonne(userAuthentication.getPersonne());
		
		if(idFournisseur !=0) {
		this.entree.setFournisseur((Fournisseur)service.getObjectById(idFournisseur, "Fournisseur"));
		}
		
		this.service.addObject(this.entree);
		
		
		fongible.setStockActuel(this.fongible.getStockActuel()+ this.entree.getQteEntree());
		service.updateObject(materiel);
		
	
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}
	
	public void chargerMateriel() {
		materiel = new Materiel();
		materiel = (Materiel) service.getObjectById(idMateriel, "Materiel");
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		setIdMateriel(0);
		setMateriel(null);
		setIdSource(0);
		setIdFournisseur(0);
		this.entree.setQteEntree(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		info("Annulation effectuée avec succès!");
		
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

	public Entree getEntree() {
		return entree;
	}

	public void setEntree(Entree entree) {
		this.entree = entree;
	}

	public int getIdFournisseur() {
		return idFournisseur;
	}

	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}

	@SuppressWarnings("unchecked")
	public List<Fournisseur> getListFournisseur() {
		 listFournisseur = service.getObjects("Fournisseur");
		 System.out.println("========Taille de la liste:"+listFournisseur.size());
		 return listFournisseur;
	}

	public void setListFournisseur(List<Fournisseur> listFournisseur) {
		this.listFournisseur = listFournisseur;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		 listMateriel = service.getObjects("Materiel");
		 System.out.println("=========Taille de la liste:"+listMateriel);
		 return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}

	/*
	 * public int getStockMateriel() { return stockMateriel; }
	 * 
	 * public void setStockMateriel(int stockMateriel) { this.stockMateriel =
	 * stockMateriel; }
	 */
	public int getIdSource() {
		return idSource;
	}

	public void setIdSource(int idSource) {
		this.idSource = idSource;
	}

	@SuppressWarnings("unchecked")
	public List<SourceFinancement> getListSourceFinance() {
		 listSourceFinance = service.getObjects("SourceFinancement");
		 System.out.println("========Taille de la liste:"+listSourceFinance.size());
		 return listSourceFinance;
	}

	public void setListSourceFinance(List<SourceFinancement> listSourceFinance) {
		this.listSourceFinance = listSourceFinance;
	}

	public int getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	@SuppressWarnings("unchecked")
	public List<Entree> getListEntree() {
		 listEntree = service.getObjects("Entree");
		 System.out.println("========Taille de la liste:"+listEntree.size());
		 return listEntree;
	}

	public void setListEntree(List<Entree> listEntree) {
		this.listEntree = listEntree;
	}

	public Fongible getFongible() {
		return fongible;
	}

	public void setFongible(Fongible fongible) {
		this.fongible = fongible;
	}

	

	
}