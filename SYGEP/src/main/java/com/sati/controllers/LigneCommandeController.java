package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Boncommande;
import com.sati.model.LigneCommande;
import com.sati.model.Materiel;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class LigneCommandeController {

	@Autowired
	Iservice  service;
	private LigneCommande ligneCommande = new LigneCommande();
	private List<LigneCommande> listLigneCommande = new ArrayList<>();
	private LigneCommande selectedLigneCommande = new LigneCommande();
	private Materiel materiel = new Materiel();
	private int idMateriel;
	private List<Materiel> listMateriel = new ArrayList<>();
	private Boncommande boncommande = new Boncommande();
	private int idBonCommande;
	private List<Boncommande> listBoncommande = new ArrayList<>();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		
	}
	
	public String genererCodeLigneCommande() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("LigneCommande").size();
		if(nbEnregistrement < 10)
			prefix = "CE00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CE0" ;
		if (nbEnregistrement > 100) 
			prefix = "CE" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	public void enregistrer() {
		materiel = (Materiel) service.getObjectById(idMateriel, "Materiel");
		boncommande = (Boncommande) service.getObjectById(idBonCommande, "BonCommande");
		
		ligneCommande.setCodeLigneCommande(genererCodeLigneCommande());
		ligneCommande.setQteLigneCommande(null);
		ligneCommande.setBoncommande(boncommande);
		ligneCommande.setMateriel(materiel);
		service.addObject(ligneCommande);
		this.info("Enregistrement effectué avec succès!");
		this.annuler();
	}
	public void annuler() {
		this.ligneCommande.setCodeLigneCommande(null);
		this.ligneCommande.setQteLigneCommande(null);
		this.setMateriel(null);
		this.setIdMateriel(0);
		this.setBoncommande(null);
		this.setIdBonCommande(0);
	}
	public void modifier() {
		service.updateObject(ligneCommande);
		this.annuler();
		this.info("Modification effectué avec succès!");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
		
	}
	
	public void selectionnerLigne() {
		this.ligneCommande = selectedLigneCommande;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}
	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}
	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	@SuppressWarnings("unchecked")
	public List<LigneCommande> getListLigneCommande() {
		listLigneCommande = service.getObjects("LigneCommande");
		System.out.println("==========Taille de la liste est:"+listLigneCommande.size());
		return listLigneCommande;
	}
	public void setListLigneCommande(List<LigneCommande> listLigneCommande) {
		this.listLigneCommande = listLigneCommande;
	}
	public LigneCommande getSelectedLigneCommande() {
		return selectedLigneCommande;
	}
	public void setSelectedLigneCommande(LigneCommande selectedLigneCommande) {
		this.selectedLigneCommande = selectedLigneCommande;
	}
	public Materiel getMateriel() {
		return materiel;
	}
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = service.getObjects("Materiel");
		System.out.println("=============Taille de la liste est:"+listMateriel.size());
		return listMateriel;
	}
	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}
	public Boncommande getBoncommande() {
		return boncommande;
	}
	public void setBoncommande(Boncommande boncommande) {
		this.boncommande = boncommande;
	}
	@SuppressWarnings("unchecked")
	public List<Boncommande> getListBoncommande() {
		listBoncommande = service.getObjects("BonCommande");
		System.out.println("==========Taille de la liste est:"+listBoncommande.size());
		return listBoncommande;
	}
	public void setListBoncommande(List<Boncommande> listBoncommande) {
		this.listBoncommande = listBoncommande;
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
	public int getIdMateriel() {
		return idMateriel;
	}
	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}
	public int getIdBonCommande() {
		return idBonCommande;
	}
	public void setIdBonCommande(int idBonCommande) {
		this.idBonCommande = idBonCommande;
	}

}
